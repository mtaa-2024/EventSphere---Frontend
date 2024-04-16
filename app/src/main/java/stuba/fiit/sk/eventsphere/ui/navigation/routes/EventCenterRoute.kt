package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import stuba.fiit.sk.eventsphere.ui.activities.event_center.EventCenterScreen
import stuba.fiit.sk.eventsphere.viewmodel.EventCenterViewModel
import stuba.fiit.sk.eventsphere.viewmodel.EventCenterViewModelFactory
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun EventCenterRoute (
    mainViewModel: MainViewModel,
    onNavigationBack: () -> Unit,
    onNavigationToEvent: (Int) -> Unit
) {
    val eventCenterViewModel: EventCenterViewModel = viewModel(factory = EventCenterViewModelFactory(mainViewModel))

    EventCenterScreen(
        viewModel = mainViewModel,
        eventCenterViewModel = eventCenterViewModel,
        toBack = onNavigationBack,
        toEvent = onNavigationToEvent
    )

}