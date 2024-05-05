package stuba.fiit.sk.eventsphere.ui.activities.register

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.ui.activities.login.TopBar
import stuba.fiit.sk.eventsphere.ui.components.ButtonComponent
import stuba.fiit.sk.eventsphere.ui.components.InputFieldComponent
import stuba.fiit.sk.eventsphere.ui.isInternetAvailable
import stuba.fiit.sk.eventsphere.ui.theme.labelStyle
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
                text = stringResource(id = R.string.register_welcome_text),
                style = welcomeStyle,
                fontSize = 25.sp
            )

            Spacer (
                modifier = Modifier
                    .height(60.dp)
            )

            InputFieldComponent (
                label = stringResource(id = R.string.username),
                text = registerViewModel.registerData.value?.username!!,
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
                label = stringResource(id = R.string.email),
                text = registerViewModel.registerData.value?.email!!,
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
                label = stringResource(id = R.string.password),
                text = registerViewModel.registerData.value?.password!!,
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
                label = stringResource(id = R.string.verify_password),
                text = registerViewModel.registerData.value?.verifyPassword!!,
                onUpdate = registerViewModel::updateRepeatedPassword,
                onCheck = null,
                keyboardType = KeyboardType.Password,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer (
                modifier = Modifier
                    .height(20.dp)
            )

            var errorMessage by remember { mutableIntStateOf(0) }

            Text (
                text = if (errorMessage != 0) stringResource(id = errorMessage) else "",
                style = labelStyle,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )

            Spacer (
                modifier = Modifier
                    .weight(1f)
            )
            val string = stringResource(id = R.string.no_internet_connection)
            val context = LocalContext.current
            ButtonComponent (
                text = stringResource(id = R.string.register_button),
                fillColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                    onClick = { viewModel.viewModelScope.launch {
                        if (isInternetAvailable(context)) {
                            if (registerViewModel.canBeCreated) {
                                val (result, message) = viewModel.registerNewUser(
                                    registerViewModel.registerData.value,
                                    registerViewModel.registerDataCopy
                                )
                                if (result) toHome() else errorMessage = message
                            } else {
                                errorMessage = R.string.check_data
                            }
                        } else {
                            Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
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