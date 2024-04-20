package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import stuba.fiit.sk.eventsphere.ui.activities.profile.ProfileScreen
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel
import stuba.fiit.sk.eventsphere.viewmodel.ProfileViewModel
import stuba.fiit.sk.eventsphere.viewmodel.ProfileViewModelFactory
import java.util.Locale

@Composable
fun ProfileRoute (
    onNavigationToLogout: () -> Unit,
    onNavigationToEventCenter: () -> Unit,
    onNavigationToBack: ()-> Unit,
    onNavigationToEditProfile: () -> Unit,
    onNavigationToFriendsScreen: (id:Int?) -> Unit,
    onNavigationToSearchUserScreen: () -> Unit,
    setLanguage: (Locale) -> Unit,
    setTheme: (Boolean) -> Unit,
    mainViewModel: MainViewModel,
) {
    val profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModelFactory(mainViewModel.loggedUser.value?.id ?:0))
    ProfileScreen (
        toLogout = onNavigationToLogout,
        back = onNavigationToBack,
        toEventCenter = onNavigationToEventCenter,
        toEditProfile = onNavigationToEditProfile,
        toFriend = onNavigationToFriendsScreen,
        toSearchUser = onNavigationToSearchUserScreen,
        viewModel = mainViewModel,
        profileViewModel = profileViewModel,
        onLanguageChange = setLanguage,
        onThemeChange = setTheme
    )
}