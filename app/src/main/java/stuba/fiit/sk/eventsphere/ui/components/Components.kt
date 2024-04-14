package stuba.fiit.sk.eventsphere.ui.components

import android.app.PendingIntent.getActivity
import android.app.PendingIntent.getService
import android.content.Context
import android.graphics.ColorFilter
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stuba.fiit.sk.eventsphere.R
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
fun CategoryBox (
    icon: Int,
    value: Boolean,
    onClick: (Boolean) -> Unit
) {
    var isSelected by remember { mutableStateOf(value) }
    
    Column (
        modifier = Modifier
            .width(50.dp)
            .height(58.dp),
        horizontalAlignment = Alignment.CenterHorizontally
        
    ) {
        Box(
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
                    if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                    RoundedCornerShape(
                        topStart = 15.dp,
                        topEnd = 15.dp,
                        bottomStart = 15.dp,
                        bottomEnd = 15.dp
                    )
                )
                .background(MaterialTheme.colorScheme.background)
                .padding(2.dp)
                .clickable(onClick = {
                    onClick(isSelected)
                    isSelected = !isSelected
                }),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(
                    id = icon
                ),
                contentDescription = "education",
                contentScale = ContentScale.Inside,
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        if (isSelected) {
            Box(modifier = Modifier
                .height(2.dp)
                .width(40.dp)
                .background(MaterialTheme.colorScheme.primary)
                .clip(
                    RoundedCornerShape(
                        topStart = 15.dp,
                        topEnd = 15.dp,
                        bottomStart = 15.dp,
                        bottomEnd = 15.dp
                    )
                )
            )
        }
    }
}

@Composable
fun HomeSelectorSelected (
    value: String
) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(30.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 15.dp,
                    topEnd = 15.dp,
                    bottomStart = 15.dp,
                    bottomEnd = 15.dp
                )
            )
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text (
            text = value,
            style = TextStyle(
                color = MaterialTheme.colorScheme.background, fontFamily = FontFamily(
                    Font(R.font.urbanist_semibold)
                )
            )
        )
    }
}

@Composable
fun HomeSelectorUnselected (
    value: String,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(30.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 15.dp,
                    topEnd = 15.dp,
                    bottomStart = 15.dp,
                    bottomEnd = 15.dp
                )
            )
            .border(
                1.dp,
                MaterialTheme.colorScheme.primary,
                RoundedCornerShape(
                    topStart = 15.dp,
                    topEnd = 15.dp,
                    bottomStart = 15.dp,
                    bottomEnd = 15.dp
                )
            )
            .background(MaterialTheme.colorScheme.background)
            .clickable(onClick = onSelect),
        contentAlignment = Alignment.Center
    ) {
        Text (
            text = value,
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground, fontFamily = FontFamily(
                    Font(R.font.urbanist_semibold)
                )
            )
        )
    }
}

@Composable
fun EventBanner (
    title: String?,
    date: String?,
    location: String?,
    icon: Int
) {
    Box (
        modifier = Modifier
            .width(340.dp)
    ) {
        Box (
            modifier = Modifier
                .width(315.dp)
                .height(80.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 15.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 15.dp
                    )
                )
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Column (
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp)
            ) {
                Text (
                    text = title ?: "",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(
                            Font(R.font.urbanist_semibold)
                        )
                    )
                )
                Text (
                    text = date ?: "",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 15.sp,
                        fontFamily = FontFamily(
                            Font(R.font.urbanist_semibold)
                        )
                    )
                )
                Text (
                    text = location ?: "",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 15.sp,
                        fontFamily = FontFamily(
                            Font(R.font.urbanist_semibold)
                        )
                    )
                )
            }
        }
        Box (
            modifier = Modifier
                .width(60.dp)
                .height(40.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 15.dp,
                        topEnd = 15.dp,
                        bottomStart = 15.dp,
                        bottomEnd = 15.dp
                    )
                )
                .background(MaterialTheme.colorScheme.primaryContainer)
                .align(Alignment.CenterEnd),
            contentAlignment = Alignment.Center
        ) {
            Image (
                painter = painterResource(id = icon),
                contentDescription = "education",
                contentScale = ContentScale.Inside
            )
        }
    }
}

@Composable
fun EventSelector (
    upComingSelected: () -> Unit,
    attendingSelected: () -> Unit,
    invitedSelected: () -> Unit

) {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        var isSelectedUpComing by remember { mutableStateOf(true) }
        var isSelectedAttending by remember { mutableStateOf(false) }
        var isSelectedInvited by remember { mutableStateOf(false) }

        if (isSelectedUpComing) HomeSelectorSelected(
            value = "Upcoming"
        ) else HomeSelectorUnselected(
            value = "Upcoming",
            onSelect = {
                isSelectedUpComing = !isSelectedUpComing
                isSelectedAttending = false
                isSelectedInvited = false
            }
        )
        if (isSelectedAttending) HomeSelectorSelected(
            value = "Attending"
        ) else HomeSelectorUnselected(
            value = "Attending",
            onSelect = {
                isSelectedUpComing = false
                isSelectedAttending = !isSelectedAttending
                isSelectedInvited = false
            }
        )
        if (isSelectedInvited) HomeSelectorSelected(
            value = "Invited"
        ) else HomeSelectorUnselected(
            value = "Invited",
            onSelect = {
                isSelectedUpComing = false
                isSelectedAttending = false
                isSelectedInvited = !isSelectedInvited
            }
        )
    }
}