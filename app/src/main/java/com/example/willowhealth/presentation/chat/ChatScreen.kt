package com.example.willowhealth.presentation.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.willowhealth.R
import com.example.willowhealth.presentation.ui.components.ChatInputFiled
import com.example.willowhealth.presentation.ui.components.ChatMessage
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun ChatScreen(viewModel: ChatViewModel = koinViewModel()) {

    if (viewModel.chatMessages.value.isEmpty())
        viewModel.setInitialText(getInitialBotMessage())

    val chatMessages by remember {
        viewModel.chatMessages
    }


    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 10.dp, 8.dp, 2.dp)
                .align(Alignment.Start)
                .weight(1f),
            state = listState,
            verticalArrangement = Arrangement.Bottom

        ) {

            items(chatMessages.size) { index ->
                ChatMessage(message = chatMessages[index])
            }
        }

        ChatInputFiled(
            text = viewModel.message.value.text,
            onTextValueChanged = viewModel::onInputChange,
            onButtonClicked = viewModel::onSendClick,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End)
                .weight(0.3f)
        )

    }


}

@Composable
fun getInitialBotMessage(): String {
    return stringResource(
        R.string.initial_gpt_message
    )

}

