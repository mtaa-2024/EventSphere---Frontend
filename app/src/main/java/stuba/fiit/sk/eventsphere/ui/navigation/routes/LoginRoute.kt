package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import stuba.fiit.sk.eventsphere.ui.activities.login.LoginScreen
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun LoginRoute(
    onNavigationToHome: () -> Unit,
    onNavigationToBack: () -> Unit,
    mainViewModel: MainViewModel
) {

    LoginScreen (
        toHome = onNavigationToHome,
        back = onNavigationToBack,
        viewModel = mainViewModel,
    )
}