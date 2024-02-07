package com.example.willowhealth


class Message(var text: String, var sentBy: String = "me") {

    companion object {
        var SENT_BY_ME = "me"
        var SENT_BY_BOT = "bot"
    }
}