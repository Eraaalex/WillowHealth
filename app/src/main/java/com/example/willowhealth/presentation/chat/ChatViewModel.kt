package com.example.willowhealth.presentation.chat

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.willowhealth.GPTService
import com.example.willowhealth.data.datasource.FirebaseRealtimeSource
import com.example.willowhealth.model.Message
import com.example.willowhealth.model.MessageType
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    var message = mutableStateOf(TextFieldValue())
        private set
    var respond = MutableLiveData<String>("")


    private val _chatMessages = mutableListOf<Message>()
    val chatMessages: List<Message> = _chatMessages


    fun sendMessage(text: String, sendBy: MessageType = MessageType.SENT_BY_ME) {
        _chatMessages.add(Message(text, sendBy))
        viewModelScope.launch {
            respond.value = GPTService.getGPTResponse(text)

        }
    }

    fun sendBotMessage() {
        _chatMessages.add(Message(respond.value ?: "", MessageType.SENT_BY_BOT))

    }

    private fun getSurveyData(){
        FirebaseRealtimeSource.
    }

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