package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import stuba.fiit.sk.eventsphere.ui.activities.profile.ProfileScreen
import stuba.fiit.sk.eventsphere.viewmodel.HomeViewModel
import stuba.fiit.sk.eventsphere.viewmodel.HomeViewModelFactory
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel
import stuba.fiit.sk.eventsphere.viewmodel.ProfileViewModel
import stuba.fiit.sk.eventsphere.viewmodel.ProfileViewModelFactory

@Composable
fun ProfileRoute(
    onNavigationToHome: () -> Unit,
    onNavigationToEventCenter: () -> Unit,
    onNavigationToBack: ()-> Unit,
    onNavigationToEditProfile: () -> Unit,
    mainViewModel: MainViewModel
) {
    val profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModelFactory(mainViewModel.loggedUser.value?.id ?:0))
    ProfileScreen (
        home = onNavigationToHome,
        toEventCenter = onNavigationToEventCenter,
        toWelcomeScreen = onNavigationToBack,
        toEditProfile = onNavigationToEditProfile,
        viewModel = mainViewModel,
        profileViewModel = profileViewModel
    )
}