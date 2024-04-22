package stuba.fiit.sk.eventsphere.ui.activities.groupchat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.model.chatUiState
import stuba.fiit.sk.eventsphere.model.observeLiveData
import stuba.fiit.sk.eventsphere.model.webSocket
import stuba.fiit.sk.eventsphere.ui.theme.labelStyle
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel


@Composable
fun GroupChat (
    toBack: () -> Unit,
    viewModel: MainViewModel
) {
    Column ( modifier = Modifier
        .fillMaxSize()
    ) {
        ChatTopBar(
            back = toBack
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 15.dp)
                .verticalScroll(rememberScrollState())
        ) {

            val chat = observeLiveData(liveData = chatUiState.messages)

            chat?.forEach { message ->
                MessageBox (
                    text = message.message,
                    id = message.id,
                    mainViewModel = viewModel
                )
            }
        }
        Spacer (
            modifier = Modifier
                .weight(1f)
        )
        InputMessage (
            onSave = webSocket::onMessage,
            modifier = Modifier,
            mainViewModel = viewModel
        )

    }
}

@Composable
fun InputMessage (
    onSave: (message: String) -> Unit,
    modifier: Modifier,
    mainViewModel: MainViewModel
) {
    var value by remember { mutableStateOf("Aa") }
    var isFocused by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField (
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .onFocusChanged { isFocused = it.isFocused },
        value = if (isFocused && value == "Aa") "" else value,
        label = { Text(text = "") },
        singleLine = false,
        onValueChange = {
            value = it
        },
        textStyle = labelStyle.copy(
            fontSize = 16.sp,
            letterSpacing = 0.sp,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        trailingIcon = {
            IconButton (
                onClick = {
                    onSave("{\"message\":\"${value}\", \"id\":${mainViewModel.loggedUser.value?.id}}")
                    value = "Aa" },
            ) {
                Icon(
                    painterResource(id = R.drawable.send),
                    contentDescription = "Icon"
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = Text, imeAction = ImeAction.Done),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = Color.Gray,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground
        ),
    )
}

@Composable
fun MessageBox (
    text: String,
    id: Int,
    mainViewModel: MainViewModel
) {
   Column (
       modifier = Modifier
           .fillMaxWidth(),
       horizontalAlignment = if (id == mainViewModel.loggedUser.value?.id) Alignment.End else Alignment.Start
   ) {
       Text (
           text = "Firstname Lastname",
           fontSize = 10.sp,
           style = labelStyle,
       )
       Spacer (
           modifier = Modifier
               .height(4.dp)
       )
       Box (
           modifier = Modifier
               .clip(RoundedCornerShape(15.dp))
               .background(if (id == mainViewModel.loggedUser.value?.id) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer)
               .padding(8.dp)
       ) {
           Text(
               text = text,
               style = labelStyle,
               fontSize = 16.sp,
               color = MaterialTheme.colorScheme.onBackground
           )
       }
   }
    Spacer (
        modifier = Modifier
            .height(20.dp)
    )
}

@Composable
fun ChatTopBar (
    back: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.top_bar),
            contentDescription = "welcome_background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
        )
        Row (
            modifier = Modifier
                .padding(10.dp)
                .matchParentSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button (
                onClick = back,
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back_arrow),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background),
                    contentDescription = "Back"
                )
            }
        }
        Box() {}
    }
}