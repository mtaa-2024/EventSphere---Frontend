package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import stuba.fiit.sk.eventsphere.ui.activities.welcome.WelcomeScreen
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun WelcomeRoute(
    onNavigationToLogin: () -> Unit,
    onNavigationToRegister: () -> Unit,
    onNavigationToHome: () -> Unit
) {
    WelcomeScreen (
        toLogin = onNavigationToLogin,
        toRegister = onNavigationToRegister,
        toHome = onNavigationToHome
    )
}