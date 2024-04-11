package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import stuba.fiit.sk.eventsphere.ui.activities.welcome.WelcomeScreen
import stuba.fiit.sk.eventsphere.viewmodel.WelcomeViewModel
import stuba.fiit.sk.eventsphere.viewmodel.WelcomeViewModelFactory

@Composable
fun WelcomeRoute(
    onNavigationToLogin: () -> Unit,
    onNavigationToRegister: () -> Unit,
    onNavigationToHome: () -> Unit
) {
    val welcomeViewModel: WelcomeViewModel = viewModel(factory = WelcomeViewModelFactory())
    WelcomeScreen (
        toLogin = onNavigationToLogin,
        toRegister = onNavigationToRegister,
        toHome = onNavigationToHome
    )
}