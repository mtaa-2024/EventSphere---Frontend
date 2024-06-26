package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import stuba.fiit.sk.eventsphere.model.Event
import stuba.fiit.sk.eventsphere.ui.activities.home.HomeScreen
import stuba.fiit.sk.eventsphere.viewmodel.HomeViewModel
import stuba.fiit.sk.eventsphere.viewmodel.HomeViewModelFactory
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun HomeRoute (
    onNavigationToProfile: () -> Unit,
    onNavigationToGroupChat: () -> Unit,
    onNavigationToEvent: (Event) -> Unit,
    onNavigationToBack: () -> Unit,
    mainViewModel: MainViewModel
) {
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(viewModel = mainViewModel))
    HomeScreen (
        toProfile = onNavigationToProfile,
        viewModel = mainViewModel,
        toEvent = onNavigationToEvent,
        toBack = onNavigationToBack,
        toGroupChat = onNavigationToGroupChat,
        homeViewModel = homeViewModel
    )
}