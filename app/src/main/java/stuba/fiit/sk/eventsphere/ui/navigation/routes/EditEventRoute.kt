package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import stuba.fiit.sk.eventsphere.model.Event
import stuba.fiit.sk.eventsphere.ui.activities.editevent.EditEventScreen
import stuba.fiit.sk.eventsphere.viewmodel.EditEventViewModel
import stuba.fiit.sk.eventsphere.viewmodel.EditEventViewModelFactory
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun EditEventRoute (
    event: Event,
    mainViewModel: MainViewModel,
    toBack: () -> Unit,
) {
    val editEventViewModel: EditEventViewModel = viewModel(factory = EditEventViewModelFactory(event, mainViewModel))

    EditEventScreen (
        mainViewModel = mainViewModel,
        editEventViewModel = editEventViewModel,
        back = toBack,
    )
}