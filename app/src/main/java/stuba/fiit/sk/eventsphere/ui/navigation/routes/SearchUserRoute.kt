package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import stuba.fiit.sk.eventsphere.ui.activities.search_user.SearchUserScreen
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel
import stuba.fiit.sk.eventsphere.viewmodel.SearchUserViewModel
import stuba.fiit.sk.eventsphere.viewmodel.SearchUserViewModelFactory

@Composable
fun SearchUserRoute(
    onNavigationToProfile: () -> Unit,
    onNavigationToFriendsScreen: () -> Unit,
    mainViewModel: MainViewModel
) {
    val searchUserViewModel: SearchUserViewModel = viewModel(factory = SearchUserViewModelFactory())
    SearchUserScreen (
        toProfile = onNavigationToProfile,
        toFriends = onNavigationToFriendsScreen,
        viewModel = mainViewModel,
        searchUserViewModel = searchUserViewModel
    )
}