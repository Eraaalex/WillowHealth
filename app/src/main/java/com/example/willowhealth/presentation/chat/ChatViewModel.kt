package com.example.willowhealth.presentation.chat

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.willowhealth.model.Message
import com.example.willowhealth.model.MessageType
import com.example.willowhealth.repository.UserRepository
import com.example.willowhealth.service.GPTService
import com.example.willowhealth.utils.toGPTRequestForm
import kotlinx.coroutines.launch

class ChatViewModel(
    private val userRepository: UserRepository,
    private val gptService: GPTService
) : ViewModel() {
    var message = mutableStateOf(TextFieldValue())
        private set
    var respond = mutableStateOf("")
        private set


    var chatMessages = mutableStateOf(listOf<Message>())


    private fun sendMessage(text: String, sendBy: MessageType = MessageType.SENT_BY_ME) {
        Log.d("Chat", "sendMessage")
        val currentMessages = chatMessages.value.toMutableList()
        currentMessages.add(Message(text, sendBy))
        Log.d("Chat", "add sendMessage")
        viewModelScope.launch {
            if (sendBy == MessageType.SENT_BY_ME) {
                val intro = getIntroductionRequest()
                respond.value = gptService.getGPTResponse(intro + text)
                currentMessages.add(Message(respond.value, MessageType.SENT_BY_BOT))
            }
            Log.d("Chat", "update sendMessage")
            chatMessages.value = currentMessages
            Log.d("Chat", "nice ")
        }

    }

    private suspend fun getIntroductionRequest(
    ): String {
        var intro: String =
            "I am writing to seek your expertise in analyzing the sleep quality data of the respondent mentioned above." +
                    " The provided survey data covers a week-long period and includes various metrics related to their sleep patterns and subjective experiences.\n" +
                    "\n" +
                    "Analysis Request:\n" +
                    "\n" +
                    "Could you please analyze the trends and patterns observed in the respondent's sleep data over the past week?\n" +
                    "Identify any potential issues or areas for improvement based on the data provided.\n" +
                    "Offer insights into factors that may be contributing to any observed sleep disturbances or poor sleep quality.\n" +
                    "Provide personalized advice or recommendations to help the respondent improve their sleep quality and overall well-being." +
                    "Survey Data: \n"

        intro += userRepository.getSurveyData(7).forEach {
            it.toGPTRequestForm()
        }
        return intro
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

    fun setInitialText(intro: String) {
        sendMessage(intro, MessageType.SENT_BY_BOT)
    }
}