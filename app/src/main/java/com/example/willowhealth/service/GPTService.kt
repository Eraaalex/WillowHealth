package com.example.willowhealth.service

import android.util.Log
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.example.willowhealth.KEY
import com.example.willowhealth.presentation.main.TAG


object GPTService {

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
            Log.d(TAG, "getGPTResponse: OK: $response")
            return response
        } catch (e: Exception) {
            Log.d(TAG, "getGPTResponse: ERROR: ${e.message ?: ""}")
            return "Error occurred " + e.message ?: ""
        }

    }
}