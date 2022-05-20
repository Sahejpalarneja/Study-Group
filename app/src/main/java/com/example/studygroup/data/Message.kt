package com.example.studygroup.data

class Message {
    var message:String? = null
    var username :String? = null

    constructor(){}
    constructor(_message:String?,_senderId:String?)
    {
        this.message = _message
        this.username = _senderId
    }
}