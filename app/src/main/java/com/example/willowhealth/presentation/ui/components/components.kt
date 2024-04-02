package com.example.willowhealth.presentation.ui.components


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.willowhealth.R
import com.example.willowhealth.app.AppRouter
import com.example.willowhealth.app.Screen
import com.example.willowhealth.model.Message
import com.example.willowhealth.model.MessageType
import com.example.willowhealth.presentation.ui.theme.WillowTheme

@Composable
fun ButtonComponent(
    text: String,
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = false
) {

    Button(
        onClick = {
            onButtonClicked()
        },
        modifier = modifier.fillMaxHeight(),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
        enabled = isEnabled,
        shape = RoundedCornerShape(18)
    ) {
        Text(text, color = MaterialTheme.colors.onPrimary)
    }
}

@Composable
fun BasicCard(
    value: Int,
    normalValue: Int,
    title: String,
    backgroundColor: Color,
    progressColor: Color,
    textColor: Color,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = backgroundColor
    ) {
        Column(
            modifier = Modifier.padding(12.dp, 8.dp)
        ) {
            Text(
                text = title,
                color = textColor,
                fontSize = 20.sp,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = value.toString(),
                        color = textColor,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "/$normalValue",
                        color = textColor,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                LinearProgressIndicator(
                    progress = value / normalValue.toFloat(),
                    modifier = Modifier
                        .height(15.dp)
                        .weight(1.5f)
                        .clip(RoundedCornerShape(8.dp)),
                    color = progressColor
                )

            }
        }

    }
}

@Composable
fun BasicSpacer() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
}


@Preview
@Composable
fun OutlinedTextFieldLogIn(
    text: String = "+123",
    label: String = stringResource(R.string.phone),
    onTextValueChanged: (String) -> Unit = {},
    visual: VisualTransformation = VisualTransformation.None
) {

    WillowTheme {
        OutlinedTextField(
            value = text,
            onValueChange = { onTextValueChanged(it) },
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = visual,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colors.onSurface,
                focusedBorderColor = MaterialTheme.colors.secondary,
                unfocusedBorderColor = MaterialTheme.colors.primary,
                backgroundColor = MaterialTheme.colors.surface,
                focusedLabelColor = MaterialTheme.colors.onSecondary,
                unfocusedLabelColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
            )
        )
    }
}

@Preview
@Composable
fun RecyclerViewSample(data: List<String> = listOf("Hello,", "world!")) {
    val items = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
    ) {
        itemsIndexed(data) { index, d ->
            ListItem(d + "$index")
        }
    }
}

@Composable
fun ListItem(data: String, modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth()) {
        Text(text = data)
    }
}


@Composable
fun ChatMessage(message: Message) {
    if (message.sentBy == MessageType.SENT_BY_ME) {
        ChatMyMessage(message = message)
    } else {
        ChatBotMessage(message = message)
    }
}

@Composable
fun ChatBotMessage(modifier: Modifier = Modifier, message: Message) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = message.text,
            modifier = Modifier
                .padding(2.dp)
                .background(MaterialTheme.colors.surface, RoundedCornerShape(5.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            color = MaterialTheme.colors.onSurface,
        )
    }
}

@Composable
fun ChatMyMessage(modifier: Modifier = Modifier, message: Message) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Text(
            text = message.text,
            modifier = Modifier
                .padding(2.dp)
                .background(MaterialTheme.colors.surface, RoundedCornerShape(5.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            color = MaterialTheme.colors.onSurface,
        )
    }
}


@Preview
@Composable
fun ChatBotMessagePreview(message: Message = Message("Hello!", MessageType.SENT_BY_BOT)) {
    ChatBotMessage(message = message)

}

@Preview
@Composable
fun ChatInputFiledPreview() {
    ChatInputFiled(text = "Hello", onButtonClicked = { })
}

@Composable
fun ChatInputFiled(
    text: String,
    onTextValueChanged: (String) -> Unit = {},
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = text,
        onValueChange = onTextValueChanged,
        maxLines = 4,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
        trailingIcon = {
            IconButton(
                onClick = { onButtonClicked() },
                modifier = Modifier.size(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Send"
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            textColor = MaterialTheme.colors.onSurface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}


@Preview
@Composable
fun ButtonsPreview() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ButtonComponent(
            text = "Sign In",
            onButtonClicked = {
                {}

            },
            isEnabled = true,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        ButtonComponent(
            text = "Sign Up",

            onButtonClicked = {
                {}
                Log.d("MyApp", "Click on Sign Up")
                AppRouter.navigateTo(Screen.MainScreen)
            },
            isEnabled = true,
            modifier = Modifier.weight(1f)
        )

    }
}
