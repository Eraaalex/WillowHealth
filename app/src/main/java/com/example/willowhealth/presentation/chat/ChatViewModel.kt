package com.example.willowhealth.presentation.chat

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.willowhealth.GPTService
import com.example.willowhealth.model.Message
import com.example.willowhealth.model.MessageType
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    var message = mutableStateOf(TextFieldValue())
        private set
    var respond = mutableStateOf("")
        private set


    var chatMessages = mutableStateOf(listOf<Message>())


    fun sendMessage(text: String, sendBy: MessageType = MessageType.SENT_BY_ME) {
//        _chatMessages.add(Message(text, sendBy))
//        viewModelScope.launch {
//            respond.value = GPTService.getGPTResponse(text)
//            _chatMessages.add(Message(respond.value ?: "", MessageType.SENT_BY_BOT))
//        }


        val currentMessages = chatMessages?.value?.toMutableList() ?: mutableListOf()
        currentMessages.add(Message(text, sendBy))
        viewModelScope.launch {
            respond.value = GPTService.getGPTResponse(text)
            currentMessages.add(Message(respond.value ?: "", MessageType.SENT_BY_BOT))
            chatMessages.value = currentMessages
        }
    }




//    private fun getSurveyData(){
//        FirebaseRealtimeSource.
//    }

    fun onSendClick() {
        val text = message.value.text
        if (text.isNotEmpty()) {
            sendMessage(text)
            message.value = TextFieldValue()
        }
    }

    fun onInputChange(newValue: String) {
        message.value = message.value.copy(newValue)
    }
}