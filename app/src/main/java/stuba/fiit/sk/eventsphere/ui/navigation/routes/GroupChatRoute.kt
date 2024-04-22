package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import stuba.fiit.sk.eventsphere.ui.activities.groupchat.GroupChat
import stuba.fiit.sk.eventsphere.viewmodel.GroupChatViewModel
import stuba.fiit.sk.eventsphere.viewmodel.GroupChatViewModelFactory
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun GroupChatRoute (
    onNavigationToBack: () -> Unit,
    mainViewModel: MainViewModel
) {
    val groupChatViewModel: GroupChatViewModel = viewModel(factory = GroupChatViewModelFactory())
    GroupChat (
        toBack = onNavigationToBack,
        viewModel = mainViewModel
    )
}