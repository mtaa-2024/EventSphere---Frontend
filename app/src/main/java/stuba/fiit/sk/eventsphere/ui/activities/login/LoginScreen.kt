package stuba.fiit.sk.eventsphere.ui.activities.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import stuba.fiit.sk.eventsphere.ui.components.InputField

@Composable
fun LoginScreen (
    toHome: () -> Unit,
    viewModel: LoginViewModel
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        InputField(label = "Username or email", value = viewModel.user, onChange = viewModel::updateUser)
        InputField(label = "Password", value = viewModel.password, onChange = viewModel::updatePassword)
    }
}

@Preview
@Composable
fun LoginScreenPreview (
) {
    LoginScreen(toHome = {}, viewModel = LoginViewModel())

}