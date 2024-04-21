package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import stuba.fiit.sk.eventsphere.ui.activities.editevent.EditEventScreen
import stuba.fiit.sk.eventsphere.viewmodel.EditEventViewModel
import stuba.fiit.sk.eventsphere.viewmodel.EditEventViewModelFactory
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun EditEventRoute (
    id: Int,
    mainViewModel: MainViewModel,
    toEvent: (id: Int) -> Unit
) {
    val editEventViewModel: EditEventViewModel = viewModel(factory = EditEventViewModelFactory(id))

    EditEventScreen (
        mainViewModel = mainViewModel,
        editEventViewModel = editEventViewModel,
        toUpdatedEvent = toEvent
    )
}