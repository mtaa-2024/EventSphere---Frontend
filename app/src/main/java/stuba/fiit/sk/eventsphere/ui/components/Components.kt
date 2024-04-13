package stuba.fiit.sk.eventsphere.ui.components

import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.ui.theme.buttonStyle
import stuba.fiit.sk.eventsphere.ui.theme.grey
import stuba.fiit.sk.eventsphere.ui.theme.inputStyle
import stuba.fiit.sk.eventsphere.ui.theme.labelStyle
import stuba.fiit.sk.eventsphere.ui.theme.smallButton

@Composable
fun PrimaryButton (
    text: String,
    onClick: () -> Unit
) {
    ElevatedButton (
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .width(350.dp)
            .height(48.dp)
    ) {
        Text(
            text = text,
            style = buttonStyle,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            letterSpacing = 0.sp,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }

}

@Composable
fun SecondaryButton (
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton (
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .width(350.dp)
            .height(48.dp)
    ) {
        Text(
            text = text,
            style = buttonStyle,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            letterSpacing = 0.sp,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}

@Composable
fun InputField (
    label: String,
    value: String,
    onChange: (String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf(value) }
    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = if (isFocused && text == label) "" else text,
        onValueChange = {
            text = it
            onChange(it)
        },
        label = { Text(text = label, style = labelStyle) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        textStyle = TextStyle(color = grey),
        modifier = Modifier
            .onFocusChanged { isFocused = it.isFocused }
            .fillMaxWidth()
    )
}

@Composable
fun InputPasswordField (
    label: String,
    value: String,
    onChange: (String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf(value) }
    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = if (isFocused && text == label) "" else text,
        onValueChange = {
            text = it
            onChange(it)
        },
        visualTransformation = PasswordVisualTransformation(),
        label = { Text(text = label, style = labelStyle) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        textStyle = TextStyle(color = grey),
        modifier = Modifier
            .onFocusChanged { isFocused = it.isFocused }
            .fillMaxWidth()
    )
}

@Composable
fun CategoryBox (
    icon: Int
) {
    Box (
        modifier = Modifier
            .width(50.dp)
            .height(50.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 15.dp,
                    topEnd = 15.dp,
                    bottomStart = 15.dp,
                    bottomEnd = 15.dp
                )
            )
            .border(
                2.dp,
                MaterialTheme.colorScheme.primary,
                RoundedCornerShape(
                    topStart = 15.dp,
                    topEnd = 15.dp,
                    bottomStart = 15.dp,
                    bottomEnd = 15.dp
                )
            )
            .background(MaterialTheme.colorScheme.background)
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        Image (
            painter = painterResource(id = icon),
            contentDescription = "education",
            contentScale = ContentScale.Inside
        )
    }
}

@Composable
fun HomeSelector (
    value: String
) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(25.dp)
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp, bottomStart = 15.dp, bottomEnd = 15.dp))
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text (
            text = value,
            style = smallButton
        )
    }
}