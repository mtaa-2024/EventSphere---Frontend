package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import stuba.fiit.sk.eventsphere.model.Event
import stuba.fiit.sk.eventsphere.ui.activities.event.EventScreen
import stuba.fiit.sk.eventsphere.viewmodel.EventViewModel
import stuba.fiit.sk.eventsphere.viewmodel.EventViewModelFactory
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun EventRoute (
    event: Event,
    mainViewModel: MainViewModel,
    onNavigationBack: () -> Unit,
    toEdit: (event: Event) -> Unit,
) {
    val eventViewModel: EventViewModel = viewModel(factory = EventViewModelFactory(event, mainViewModel))

    EventScreen(
        viewModel = mainViewModel,
        eventViewModel = eventViewModel,
        toBack = onNavigationBack,
        toEdit = toEdit
    )

}