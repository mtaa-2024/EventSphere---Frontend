package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.ui.activities.home.HomeScreen
import stuba.fiit.sk.eventsphere.ui.activities.home.HomeViewModel
import stuba.fiit.sk.eventsphere.ui.activities.home.HomeViewModelFactory
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModelFactory

@Composable
fun HomeRoute (
    onNavigationToProfile: () -> Unit,
    onNavigationToBack: () -> Unit,
    mainViewModel: MainViewModel
) {
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory())
    HomeScreen (
        profile = onNavigationToProfile,
        back = onNavigationToBack,
        viewModel = mainViewModel,
        homeViewModel = homeViewModel
    )
}