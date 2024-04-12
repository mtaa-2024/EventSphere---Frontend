package stuba.fiit.sk.eventsphere.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stuba.fiit.sk.eventsphere.ui.theme.buttonStyle
import stuba.fiit.sk.eventsphere.ui.theme.grey
import stuba.fiit.sk.eventsphere.ui.theme.labelStyle

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
fun TopBar (

) {

}