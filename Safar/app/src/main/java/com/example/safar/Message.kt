package com.example.safar

data class Message(val content: String, val type: Int) {
    companion object {
        const val TYPE_RECEIVED = 0
        const val TYPE_SENT = 1
        const val TYPE_RECEIVED_IMAGE = 2
        const val TYPE_SENT_IMAGE = 3
    }
}
