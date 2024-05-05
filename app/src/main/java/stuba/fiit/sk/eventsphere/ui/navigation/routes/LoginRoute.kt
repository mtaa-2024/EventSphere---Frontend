package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.model.LoginData
import stuba.fiit.sk.eventsphere.ui.activities.login.LoginScreen
import stuba.fiit.sk.eventsphere.viewmodel.LoginViewModel
import stuba.fiit.sk.eventsphere.viewmodel.LoginViewModelFactory
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun LoginRoute(
    onNavigationToHome: () -> Unit,
    onNavigationToBack: () -> Unit,
    mainViewModel: MainViewModel
) {
    val loginData = LoginData (
        username = "",
        password = "",
    )
    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(loginData))
    LoginScreen (
        toHome = onNavigationToHome,
        back = onNavigationToBack,
        viewModel = mainViewModel,
        loginViewModel = loginViewModel
    )
}