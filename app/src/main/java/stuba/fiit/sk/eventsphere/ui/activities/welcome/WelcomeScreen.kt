package stuba.fiit.sk.eventsphere.ui.activities.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle

@Composable
fun WelcomeScreen (
    toLogin: () -> Unit,
    toRegister: () -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(onClick = toLogin) {
            
        }
        Text(text = "Welcome screen", style = welcomeStyle)
    }
}

@Preview
@Composable
fun WelcomeScreenPreview (

) {
   // WelcomeScreen(toLogin = {  }, toRegister = {  })
}