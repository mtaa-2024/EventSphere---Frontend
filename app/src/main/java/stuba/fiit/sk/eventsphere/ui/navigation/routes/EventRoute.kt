package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import stuba.fiit.sk.eventsphere.ui.activities.event.EventScreen
import stuba.fiit.sk.eventsphere.viewmodel.EventViewModel
import stuba.fiit.sk.eventsphere.viewmodel.EventViewModelFactory
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun EventRoute (
    id: Int,
    mainViewModel: MainViewModel,
    onNavigationBack: () -> Unit,
    toEdit: (id: Int) -> Unit,
) {
    val eventViewModel: EventViewModel = viewModel(factory = EventViewModelFactory(id))

    EventScreen(
        viewModel = mainViewModel,
        eventViewModel = eventViewModel,
        toBack = onNavigationBack,
        toEdit = toEdit
    )

}