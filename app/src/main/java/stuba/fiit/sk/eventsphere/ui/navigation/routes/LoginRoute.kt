package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import stuba.fiit.sk.eventsphere.ui.activities.login.LoginScreen

@Composable
fun LoginRoute(
    onNavigationToHome: () -> Unit,
    onNavigationToBack: () -> Unit
) {
    LoginScreen (
        toHome = onNavigationToHome,
        back = onNavigationToBack
    )
}