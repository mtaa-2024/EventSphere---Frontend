package stuba.fiit.sk.eventsphere.ui.activities.welcome

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

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