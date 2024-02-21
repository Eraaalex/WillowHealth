package com.example.willowhealth.presentation.chat

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.willowhealth.data.datasource.FirebaseAuthDataSource
import com.example.willowhealth.data.datasource.FirebaseRealtimeSource
import com.example.willowhealth.model.Message
import com.example.willowhealth.model.MessageType
import com.example.willowhealth.model.SurveyData
import com.example.willowhealth.presentation.main.TAG
import com.example.willowhealth.service.GPTService
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    var message = mutableStateOf(TextFieldValue())
        private set
    var respond = mutableStateOf("")
        private set


    var chatMessages = mutableStateOf(listOf<Message>())


    fun sendMessage(text: String, sendBy: MessageType = MessageType.SENT_BY_ME) {
        val currentMessages = chatMessages.value.toMutableList()
        currentMessages.add(Message(text, sendBy))

        viewModelScope.launch {
            getSurveyData()
            respond.value = GPTService.getGPTResponse(text)
            currentMessages.add(Message(respond.value, MessageType.SENT_BY_BOT))
            chatMessages.value = currentMessages
        }
    }


    fun getSurveyData(
        userId: String = FirebaseAuthDataSource.getCurrentUser()?.uid ?: ""
    ): LiveData<List<SurveyData>> {
        val surveyDataLiveData = MutableLiveData<List<SurveyData>>()

        viewModelScope.launch {
            try {
                val surveyDataList = FirebaseRealtimeSource.fetchSurveyData(userId)
                surveyDataLiveData.postValue(surveyDataList)
                Log.e(TAG, "[VM]:" + surveyDataList.toString())
            } catch (e: Exception) {
                // Handle exception if needed
                Log.e(TAG, "Error fetching survey data: ${e.message}")
            }
        }

        return surveyDataLiveData
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