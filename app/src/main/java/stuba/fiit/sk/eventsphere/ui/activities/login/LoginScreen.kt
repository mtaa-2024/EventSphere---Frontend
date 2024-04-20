package stuba.fiit.sk.eventsphere.ui.activities.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.ui.components.ButtonComponent
import stuba.fiit.sk.eventsphere.ui.components.InputFieldComponent
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.LoginViewModel
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Preview(showBackground = true)
@Composable
fun Preview () {
    LoginScreen(toHome = {}, back = {}, viewModel = MainViewModel(), loginViewModel = LoginViewModel())
}

@Preview(showBackground = true, locale = "sk")
@Composable
fun PreviewSk () {
    LoginScreen(toHome = {}, back = {}, viewModel = MainViewModel(), loginViewModel = LoginViewModel())
}

@Composable
fun LoginScreen (
    toHome: () -> Unit,
    back: () -> Unit,
    viewModel: MainViewModel,
    loginViewModel: LoginViewModel
) {
    loginViewModel.login.value?.user = stringResource(id = R.string.enter_username_or_email_input_text)
    loginViewModel.login.value?.password = stringResource(id = R.string.password_input_text)

    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar (back = back)

        Column (
            modifier = Modifier
                .padding(25.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text (
                text = stringResource(id = R.string.login_welcome_text),
                style = welcomeStyle,
                fontSize = 25.sp
            )

            Spacer (
                modifier = Modifier
                    .height(60.dp)
            )

            InputFieldComponent (
                label = stringResource(id = R.string.username_or_email_label),
                text = loginViewModel.login.value?.user.toString(),
                onUpdate = loginViewModel::updateUser,
                keyboardType = KeyboardType.Text,
                onCheck = null,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(
                modifier = Modifier
                    .height(40.dp)
            )

            InputFieldComponent (
                label = stringResource(id = R.string.password_label),
                text = loginViewModel.login.value?.password.toString(),
                onUpdate = loginViewModel::updatePassword,
                keyboardType = KeyboardType.Password,
                onCheck = null,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer (
                modifier = Modifier
                    .weight(1f)
            )

            ButtonComponent (
                fillColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.background,
                text = stringResource(id = R.string.login_button),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                onClick = {
                    viewModel.viewModelScope.launch {
                        if (viewModel.authenticateUser(loginViewModel.login.value)) {
                            toHome()
                        }
                    }
                }
            )

            Spacer (
                modifier = Modifier
                    .height(50.dp)
            )
        }
    }
}

@Composable
fun TopBar (
    back: () -> Unit
) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
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
                Image (
                    painter = painterResource(id = R.drawable.back_arrow),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background),
                    contentDescription = "Back"
                )
            }
        }
    }
}