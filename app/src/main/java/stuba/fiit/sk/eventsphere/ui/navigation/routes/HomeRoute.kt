package stuba.fiit.sk.eventsphere.ui.navigation.routes

import androidx.compose.runtime.Composable
import stuba.fiit.sk.eventsphere.ui.activities.home.HomeScreen

@Composable
fun HomeRoute (
    onNavigationToProfile: () -> Unit,
    onNavigationToBack: () -> Unit
) {
    HomeScreen (
        profile = onNavigationToProfile,
        back = onNavigationToBack
    )
}