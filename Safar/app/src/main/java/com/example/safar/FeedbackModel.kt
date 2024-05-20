package com.example.safar

class FeedbackModel
{
    var id: Int = 0
    var message: String = ""

    constructor()

    constructor(id: Int, message: String) {
        this.id = id
        this.message = message
    }
}
