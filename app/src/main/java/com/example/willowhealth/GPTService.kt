package com.example.willowhealth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI


object GPTService : ViewModel() {


    private val CHAT_GPT_API_KEY = KEY // Key is defined in Key.kt
    private val openAI = OpenAI(CHAT_GPT_API_KEY)

    @OptIn(BetaOpenAI::class)
    suspend fun getGPTResponse(prompt: String): String {
        try {
            val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId("gpt-3.5-turbo"),
                messages = listOf(
                    ChatMessage(
                        role = ChatRole.User,
                        content = prompt
                    )
                )
            )
            val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)
            val response = completion.choices.first().message?.content ?: ""
            Log.d("MyApp", "getGPTResponse: OK: $response")
            return response
        } catch (e: Exception) {
            Log.d("MyApp", "getGPTResponse: ERROR: ${e.message ?: ""}")
            return "Error occurred"
        }

    }
}