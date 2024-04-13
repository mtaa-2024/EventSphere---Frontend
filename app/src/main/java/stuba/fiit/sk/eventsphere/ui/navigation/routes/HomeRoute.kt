package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import stuba.fiit.sk.eventsphere.ui.activities.home.HomeScreen
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun HomeRoute (
    onNavigationToProfile: () -> Unit,
    onNavigationToBack: () -> Unit,
    mainViewModel: MainViewModel
) {
    HomeScreen (
        profile = onNavigationToProfile,
        back = onNavigationToBack,
        viewModel = mainViewModel
    )
}