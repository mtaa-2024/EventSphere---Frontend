package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.model.Event
import stuba.fiit.sk.eventsphere.ui.activities.create_event.CreateEventScreen
import stuba.fiit.sk.eventsphere.viewmodel.CreateEventViewModel
import stuba.fiit.sk.eventsphere.viewmodel.CreateEventViewModelFactory
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel
import java.util.UUID
import java.util.UUID.randomUUID

@Composable
fun CreateEventRoute (
    onNavigationToBack: () -> Unit,
    mainViewModel: MainViewModel
) {
    val initializeData: Event = Event (
        title = "",
        description = "",
        id = randomUUID(),
        ownerId = randomUUID(),
        category = 0,
        longitude = 0.0,
        latitude = 0.0,
        location = "",
        performers = null,
        comments = null,
        estimatedEnd = ""
    )
    val createEventViewModel: CreateEventViewModel = viewModel(factory = CreateEventViewModelFactory(mainViewModel, initializeData))
    CreateEventScreen(
        back = onNavigationToBack,
        viewModel = mainViewModel,
        createEventViewModel = createEventViewModel
    )
}