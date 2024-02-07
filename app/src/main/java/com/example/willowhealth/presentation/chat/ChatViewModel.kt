package com.example.willowhealth.presentation.chat

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.willowhealth.Message
import com.example.willowhealth.presentation.authentification.LoginUiState

class ChatViewModel : ViewModel() {
   var message = mutableStateOf(TextFieldValue())
        private set


    private val _chatMessages = mutableListOf<Message>()
    val chatMessages: List<Message> = _chatMessages




    fun sendMessage(text: String, sendBy : String = "me") {
        _chatMessages.add(Message(text, sendBy))
    }

fun onSendClick(){
    val text = message.value.text
    if (text.isNotEmpty()) {

            _chatMessages.add( Message(text))

        message.value = TextFieldValue() // Clear the input field after sending message
    }
}

    fun onInputChange(newValue: String) {
        message.value = message.value.copy(newValue)
    }
}