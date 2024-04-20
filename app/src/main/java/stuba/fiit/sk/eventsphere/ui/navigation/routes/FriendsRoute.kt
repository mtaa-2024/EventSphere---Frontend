package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import stuba.fiit.sk.eventsphere.ui.activities.friend.FriendsScreen
import stuba.fiit.sk.eventsphere.viewmodel.FriendsViewModel
import stuba.fiit.sk.eventsphere.viewmodel.FriendsViewModelFactory
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun FriendsRoute (
    friendId: Int,
    onNavigationToProfile: () -> Unit,
    mainViewModel: MainViewModel
) {
    val friendsViewModel: FriendsViewModel = viewModel(factory = FriendsViewModelFactory(friendId, mainViewModel.loggedUser.value?.id ?: 0))
    FriendsScreen (
        toProfile = onNavigationToProfile,
        viewModel = mainViewModel,
        friendsViewModel = friendsViewModel
    )
}