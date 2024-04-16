package stuba.fiit.sk.eventsphere.ui.activities.register

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.ui.components.InputField
import stuba.fiit.sk.eventsphere.ui.components.InputPasswordField
import stuba.fiit.sk.eventsphere.ui.components.PrimaryButton
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel
import stuba.fiit.sk.eventsphere.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen (
    toHome: () -> Unit,
    back: () -> Unit,
    viewModel: MainViewModel,
    registerViewModel: RegisterViewModel

) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.top_bar),
                contentDescription = "welcome_background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .matchParentSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )       {
                Button (
                    onClick = back,
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                ) {
                    Image (
                        painter = painterResource(id = R.drawable.back_arrow),
                        contentDescription = "Back"
                    )
                }
            }
        }
        Column (
            modifier = Modifier
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Step into tomorrow's world of events, effortlessly ",
                style = welcomeStyle,
                fontSize = 25.sp
            )
            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )
            InputField (
                label = "Username",
                value = registerViewModel.registerData.value?.username.toString(),
                onChange = registerViewModel::updateUsername
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            InputField (
                label = "Email",
                value = registerViewModel.registerData.value?.email.toString(),
                onChange = registerViewModel::updateEmail
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            InputPasswordField (
                label = "Password",
                value = registerViewModel.registerData.value?.password.toString(),
                onChange = registerViewModel::updatePassword
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            InputPasswordField (
                label = "Password",
                value = registerViewModel.registerData.value?.repeatPassword.toString(),
                onChange = registerViewModel::updateRepeatedPassword
            )

            Spacer (
                modifier = Modifier
                    .weight(1f)
            )


            PrimaryButton (text = "Register", onClick = {
                viewModel.viewModelScope.launch {
                    if (viewModel.registerNewUser(registerViewModel.registerData.value?.username.toString(), registerViewModel.registerData.value?.email.toString(), registerViewModel.registerData.value?.password.toString(),registerViewModel.registerData.value?.repeatPassword.toString())) {
                        toHome()
                    }

                }
            })

            Spacer (
                modifier = Modifier
                    .height(25.dp)
            )
        }
    }
}