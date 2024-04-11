package stuba.fiit.sk.eventsphere.ui.activities.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import stuba.fiit.sk.eventsphere.ui.theme.buttonStyle

@Composable
fun LoginScreen (
    toHome: () -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(onClick = toHome) {

        }
        Text(text = "Login screen", style = buttonStyle)
    }
}