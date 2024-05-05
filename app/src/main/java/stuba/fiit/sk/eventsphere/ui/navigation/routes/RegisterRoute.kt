package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.model.RegisterData
import stuba.fiit.sk.eventsphere.ui.activities.register.RegisterScreen
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel
import stuba.fiit.sk.eventsphere.viewmodel.RegisterViewModel
import stuba.fiit.sk.eventsphere.viewmodel.RegisterViewModelFactory

@Composable
fun RegisterRoute(
    onNavigationToHome: () -> Unit,
    onNavigationToBack: () -> Unit,
    mainViewModel: MainViewModel
) {
    val registrationData = RegisterData (
        username = "",
        email = "",
        password = "",
        verifyPassword = ""
    )
    val registerViewModel: RegisterViewModel = viewModel(factory = RegisterViewModelFactory(registrationData))
    RegisterScreen (
        toHome = onNavigationToHome,
        back = onNavigationToBack,
        viewModel = mainViewModel,
        registerViewModel = registerViewModel
    )
}