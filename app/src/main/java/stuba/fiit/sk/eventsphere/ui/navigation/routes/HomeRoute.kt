package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import stuba.fiit.sk.eventsphere.ui.activities.home.HomeScreen
import stuba.fiit.sk.eventsphere.viewmodel.HomeViewModel
import stuba.fiit.sk.eventsphere.viewmodel.HomeViewModelFactory
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun HomeRoute (
    onNavigationToProfile: () -> Unit,
    onNavigationToEvent: (Int) -> Unit,
    onNavigationToBack: () -> Unit,
    mainViewModel: MainViewModel
) {
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory())
    HomeScreen (
        toProfile = onNavigationToProfile,
        viewModel = mainViewModel,
        toEvent = onNavigationToEvent,
        toBack = onNavigationToBack,
        homeViewModel = homeViewModel
    )
}