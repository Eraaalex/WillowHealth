package com.example.willowhealth.model

class Message(var text: String, var sentBy: MessageType = MessageType.SENT_BY_ME)

enum class MessageType {
    SENT_BY_ME,
    SENT_BY_BOT
}