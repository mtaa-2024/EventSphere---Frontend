package stuba.fiit.sk.eventsphere.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.EDITPROFILE_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.EVENTCENTER_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.EVENT_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.FRIENDS_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.HOME_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.LOGIN_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.PROFILE_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.REGISTER_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.WELCOME_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.routes.EditProfileRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.EventCenterRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.EventRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.FriendsRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.HomeRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.LoginRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.ProfileRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.RegisterRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.WelcomeRoute
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModelFactory

object Destinations {
    const val WELCOME_ROUTE = "welcome"
    const val LOGIN_ROUTE = "login"
    const val REGISTER_ROUTE = "register"
    const val HOME_ROUTE = "home"
    const val EVENT_ROUTE = "event"
    const val EVENTCENTER_ROUTE = "eventcenter"
    const val PROFILE_ROUTE = "profile"
    const val EDITPROFILE_ROUTE = "editprofile"
    const val  FRIENDS_ROUTE = "friends"
}

@Composable
fun EventSphereNavHost(
    navController: NavHostController = rememberNavController(),
) {
    var mainViewModel: MainViewModel = viewModel(factory = MainViewModelFactory())

    NavHost(
        navController = navController,
        startDestination = WELCOME_ROUTE
    ) {
        composable(WELCOME_ROUTE) {
            mainViewModel = viewModel(factory = MainViewModelFactory())
            WelcomeRoute(
                onNavigationToLogin = {
                    navController.navigate(LOGIN_ROUTE)
                },
                onNavigationToRegister = {
                    navController.navigate(REGISTER_ROUTE)
                },
                onNavigationToHome = {
                    navController.navigate(HOME_ROUTE)
                }
            )
        }
        composable(LOGIN_ROUTE) {
            LoginRoute(
                onNavigationToHome = {
                    navController.navigate(HOME_ROUTE)
                },
                onNavigationToBack = {
                    navController.navigate(WELCOME_ROUTE)
                },
                mainViewModel = mainViewModel
            )
        }
        composable(REGISTER_ROUTE) {
            RegisterRoute(
                onNavigationToHome = {
                    navController.navigate(HOME_ROUTE)
                },
                onNavigationToBack = {
                    navController.navigate(WELCOME_ROUTE)
                },
                mainViewModel = mainViewModel
            )
        }

        composable(HOME_ROUTE) {
            HomeRoute(
                onNavigationToProfile = {
                    navController.navigate(PROFILE_ROUTE)
                },
                onNavigationToEvent = { eventId: Int ->
                    navController.navigate("$EVENT_ROUTE/$eventId/$HOME_ROUTE")
                },
                mainViewModel = mainViewModel
            )
        }

        composable("$EVENT_ROUTE/{eventId}/{route}") { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")?.toIntOrNull() ?: -1
            val route = backStackEntry.arguments?.getString("route")
            EventRoute(
                eventId,
                mainViewModel = mainViewModel,
                onNavigationBack = {
                    if (route == "home")
                        navController.navigate(HOME_ROUTE)
                    else
                        navController.navigate(EVENTCENTER_ROUTE)
                }
            )
        }

        composable(EVENTCENTER_ROUTE) {
            EventCenterRoute (
                mainViewModel = mainViewModel,
                onNavigationBack = {
                    navController.navigate(PROFILE_ROUTE)
                },
                onNavigationToEvent = { eventId: Int ->
                    navController.navigate("$EVENT_ROUTE/$eventId/$EVENTCENTER_ROUTE")
                },
            )
        }

        composable(PROFILE_ROUTE) {
            ProfileRoute(
                onNavigationToHome = {
                    navController.navigate(HOME_ROUTE)
                },
                onNavigationToBack = {
                    navController.navigate(WELCOME_ROUTE)
                },
                onNavigationToEventCenter = {
                    navController.navigate(EVENTCENTER_ROUTE)
                },
                onNavigationToEditProfile = {
                    navController.navigate(EDITPROFILE_ROUTE)
                },
                onNavigationToFriendsScreen = {
                    navController.navigate(FRIENDS_ROUTE)
                },
                mainViewModel = mainViewModel
            )
        }

        composable(EDITPROFILE_ROUTE) {
            EditProfileRoute(
                onNavigationToProfile = {
                    navController.navigate(PROFILE_ROUTE)
                },
                mainViewModel = mainViewModel
            )
        }

        composable(FRIENDS_ROUTE) {
            FriendsRoute(
                onNavigationToProfile = {
                    navController.navigate(PROFILE_ROUTE)
                },
                mainViewModel = mainViewModel
            )
        }
    }
}
