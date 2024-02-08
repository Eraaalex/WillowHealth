package com.example.willowhealth.presentation.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.willowhealth.presentation.ui.components.ChatInputFiled
import com.example.willowhealth.presentation.ui.components.ChatMessage

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun ChatScreen(viewModel: ChatViewModel = viewModel()) {

    val message by remember { viewModel.message }
    val respond = viewModel.respond.observeAsState()

    LaunchedEffect(respond) {
        viewModel.sendBotMessage()
    }


    val chatMessages = remember {
        viewModel.chatMessages
    }

    val listState = rememberLazyListState()
    val (lazyColumn, chatInputField) = createRefs()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp, 50.dp)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
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

