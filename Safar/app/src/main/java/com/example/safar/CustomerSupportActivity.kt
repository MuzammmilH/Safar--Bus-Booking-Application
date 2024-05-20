package com.example.safar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class CustomerSupportActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatAdapter
    private var messageList: MutableList<Message> = mutableListOf()
    private lateinit var inputEditText: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var attachmentButton: ImageButton
    private lateinit var database: DatabaseReference
    private lateinit var messageListener: ValueEventListener

    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.customer_support)

        recyclerView = findViewById(R.id.chatMessagesRecyclerView)
        inputEditText = findViewById(R.id.chat_input_edittext)
        sendButton = findViewById(R.id.send_button)
        attachmentButton = findViewById(R.id.attachment_button)

        adapter = ChatAdapter(messageList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        database = FirebaseDatabase.getInstance().reference.child("messages")

        sendButton.setOnClickListener {
            sendMessage()
        }

        attachmentButton.setOnClickListener {
            openGallery()
        }

        // Load messages from Firebase
        loadMessages()
    }

    private fun loadMessages() {
        messageListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (messageSnapshot in snapshot.children) {
                    val content = messageSnapshot.child("content").getValue(String::class.java) ?: ""
                    val type = messageSnapshot.child("type").getValue(Int::class.java) ?: Message.TYPE_RECEIVED
                    val message = Message(content, type)
                    messageList.add(message)
                }
                adapter.notifyDataSetChanged()
                recyclerView.smoothScrollToPosition(messageList.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        }

        database.addValueEventListener(messageListener)
    }

    private fun sendMessage() {
        val messageContent = inputEditText.text.toString().trim()
        if (messageContent.isNotEmpty()) {
            val messageId = database.push().key
            val messageMap = messageId?.let {
                mapOf(
                    "content" to messageContent,
                    "type" to Message.TYPE_SENT
                )
            }
            messageId?.let { database.child(it).setValue(messageMap) }
            inputEditText.setText("")
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            if (imageUri != null) {
                val messageId = database.push().key
                val messageMap = messageId?.let {
                    mapOf(
                        "content" to imageUri.toString(),
                        "type" to Message.TYPE_SENT_IMAGE
                    )
                }
                messageId?.let { database.child(it).setValue(messageMap) }

                // Save image to Firebase Storage
                val imageRef = FirebaseStorage.getInstance().reference.child("images/${UUID.randomUUID()}")
                val uploadTask = imageRef.putFile(imageUri)
                uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    imageRef.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        // Update the message with image URL
                        messageId?.let {
                            val messageMap = mapOf(
                                "content" to downloadUri.toString(),
                                "type" to Message.TYPE_SENT_IMAGE
                            )
                            database.child(it).setValue(messageMap)
                        }
                    } else {
                        // Handle failures
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove ValueEventListener to avoid memory leaks
        database.removeEventListener(messageListener)
    }
}
