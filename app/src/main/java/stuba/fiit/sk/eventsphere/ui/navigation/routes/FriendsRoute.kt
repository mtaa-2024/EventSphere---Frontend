package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import stuba.fiit.sk.eventsphere.ui.activities.friends.FriendsScreen
import stuba.fiit.sk.eventsphere.viewmodel.FriendsViewModel
import stuba.fiit.sk.eventsphere.viewmodel.FriendsViewModelFactory
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun FriendsRoute (
    onNavigationToProfile: () -> Unit,
    mainViewModel: MainViewModel
) {
    val friendsViewModel: FriendsViewModel = viewModel(factory = FriendsViewModelFactory())
    FriendsScreen (
        toProfile = onNavigationToProfile,
        viewModel = mainViewModel,
        friendsViewModel = friendsViewModel
    )
}