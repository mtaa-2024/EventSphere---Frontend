package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import stuba.fiit.sk.eventsphere.ui.activities.create_event.CreateEventScreen
import stuba.fiit.sk.eventsphere.viewmodel.CreateEventViewModel
import stuba.fiit.sk.eventsphere.viewmodel.CreateEventViewModelFactory
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun CreateEventRoute (
    onNavigationToBack: () -> Unit,
    mainViewModel: MainViewModel
) {
    val createEventViewModel: CreateEventViewModel = viewModel(factory = CreateEventViewModelFactory(mainViewModel))
    CreateEventScreen(
        back = onNavigationToBack,
        viewModel = mainViewModel,
        createEventViewModel = createEventViewModel
    )
}