package stuba.fiit.sk.eventsphere.ui.activities.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.ui.activities.login.TopBar
import stuba.fiit.sk.eventsphere.ui.components.ButtonComponent
import stuba.fiit.sk.eventsphere.ui.components.InputFieldComponent
import stuba.fiit.sk.eventsphere.ui.theme.LightColorScheme
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel
import stuba.fiit.sk.eventsphere.viewmodel.RegisterViewModel

@Preview(showBackground = true)
@Composable
fun Preview () {
    RegisterScreen(toHome = {}, back = {}, viewModel = MainViewModel(), registerViewModel = RegisterViewModel())
}

@Composable
fun RegisterScreen (
    toHome: () -> Unit,
    back: () -> Unit,
    viewModel: MainViewModel,
    registerViewModel: RegisterViewModel

) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(back)

        Column (
            modifier = Modifier
                .padding(25.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text (
                text = "Step into tomorrow's world of events, effortlessly!",
                style = welcomeStyle,
                fontSize = 25.sp
            )

            Spacer (
                modifier = Modifier
                    .height(60.dp)
            )

            InputFieldComponent (
                label = "Username",
                text = registerViewModel.register.value?.username.toString(),
                onUpdate = registerViewModel::updateUsername,
                onCheck = registerViewModel::checkUsername,
                keyboardType = KeyboardType.Text,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            InputFieldComponent (
                label = "Email",
                text = registerViewModel.register.value?.email.toString(),
                onUpdate = registerViewModel::updateEmail,
                onCheck =  registerViewModel::checkEmail,
                keyboardType = KeyboardType.Text,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            InputFieldComponent (
                label = "Password",
                text = registerViewModel.register.value?.password.toString(),
                onUpdate = registerViewModel::updatePassword,
                onCheck = null,
                keyboardType = KeyboardType.Password,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            InputFieldComponent (
                label = "Verify password",
                text = registerViewModel.register.value?.verifyPassword.toString(),
                onUpdate = registerViewModel::updateRepeatedPassword,
                onCheck = null,
                keyboardType = KeyboardType.Password,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer (
                modifier = Modifier
                    .weight(1f)
            )

            ButtonComponent (
                text = "Register",
                fillColor = LightColorScheme.primary,
                textColor = LightColorScheme.background,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                onClick = { viewModel.viewModelScope.launch {
                    if (viewModel.registerNewUser(registerViewModel.register.value)) {
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