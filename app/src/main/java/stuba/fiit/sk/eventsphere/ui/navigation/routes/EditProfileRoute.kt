package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import stuba.fiit.sk.eventsphere.ui.activities.edit_profile.EditProfileScreen
import stuba.fiit.sk.eventsphere.viewmodel.EditProfileViewModel
import stuba.fiit.sk.eventsphere.viewmodel.EditProfileViewModelFactory
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun EditProfileRoute(
    onNavigationToProfile: () -> Unit,
    mainViewModel: MainViewModel
) {
    val editProfileViewModel: EditProfileViewModel = viewModel(factory = EditProfileViewModelFactory(mainViewModel))
    EditProfileScreen (
        back = onNavigationToProfile,
        viewModel = mainViewModel,
        editProfileViewModel = editProfileViewModel
    )
}