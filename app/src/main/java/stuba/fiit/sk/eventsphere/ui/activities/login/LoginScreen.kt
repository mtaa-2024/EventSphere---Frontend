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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.ui.components.InputField
import stuba.fiit.sk.eventsphere.ui.components.InputPasswordField
import stuba.fiit.sk.eventsphere.ui.components.PrimaryButton
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.LoginViewModel

@Composable
fun LoginScreen (
    toHome: () -> Unit,
    back: () -> Unit,
    viewModel: LoginViewModel
) {
    Column (
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
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row(
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
                text = "Welcome back",
                style = welcomeStyle,
                fontSize = 25.sp
            )
            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )
            InputField(
                label = "Username or email",
                value = viewModel.user,
                onChange = viewModel::updateUser
            )

            Spacer(
                modifier = Modifier
                    .height(25.dp)
            )

            InputPasswordField(
                label = "Password",
                value = viewModel.password,
                onChange = viewModel::updatePassword
            )

            Spacer (
                modifier = Modifier
                    .weight(1f)
            )

            PrimaryButton(text = "Login", onClick = {
                viewModel.viewModelScope.launch {
                    viewModel.onLogin()
                }
            })

            Spacer(
                modifier = Modifier
                    .height(25.dp)
            )

        }
    }
}

@Preview
@Composable
fun LoginScreenPreview (
) {
    LoginScreen(toHome = {}, viewModel = LoginViewModel(), back = {})
}