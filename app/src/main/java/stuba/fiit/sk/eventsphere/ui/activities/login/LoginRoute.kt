package stuba.fiit.sk.eventsphere.ui.activities.login

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginRoute(
    onNavigationToHome: () -> Unit,
    loginViewModel: LoginViewModel
) {
    LoginScreen (
        toHome = onNavigationToHome,
        viewModel = loginViewModel
    )
}