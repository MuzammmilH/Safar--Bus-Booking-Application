package com.example.safar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ChatAdapter(private val messageList: List<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sentMessageText: TextView = itemView.findViewById(R.id.sent_message_text)
        private val sentImageView: ImageView = itemView.findViewById(R.id.sent_image_view)

        fun bind(message: Message) {
            if (message.type == Message.TYPE_SENT_IMAGE) {
                sentMessageText.visibility = View.GONE
                sentImageView.visibility = View.VISIBLE
                Glide.with(itemView.context)
                    .load(message.content)
                    .into(sentImageView)
            } else {
                sentMessageText.visibility = View.VISIBLE
                sentImageView.visibility = View.GONE
                sentMessageText.text = message.content
            }
        }
    }

    inner class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val receivedMessageText: TextView = itemView.findViewById(R.id.received_message_text)
        private val receivedImageView: ImageView = itemView.findViewById(R.id.received_image_view)

        fun bind(message: Message) {
            if (message.type == Message.TYPE_RECEIVED_IMAGE) {
                receivedMessageText.visibility = View.GONE
                receivedImageView.visibility = View.VISIBLE
                Glide.with(itemView.context)
                    .load(message.content)
                    .into(receivedImageView)
            } else {
                receivedMessageText.visibility = View.VISIBLE
                receivedImageView.visibility = View.GONE
                receivedMessageText.text = message.content
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Message.TYPE_SENT || viewType == Message.TYPE_SENT_IMAGE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.sent_message_item, parent, false)
            SentMessageViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.received_message_item, parent, false)
            ReceivedMessageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messageList[position]
        if (holder is SentMessageViewHolder) {
            holder.bind(message)
        } else if (holder is ReceivedMessageViewHolder) {
            holder.bind(message)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        return messageList[position].type
    }
}
