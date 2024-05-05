package stuba.fiit.sk.eventsphere.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import stuba.fiit.sk.eventsphere.model.Event
import stuba.fiit.sk.eventsphere.model.User
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.CREATEEVENT_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.EDITEVENT_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.EDITPROFILE_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.EVENTCENTER_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.EVENT_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.FRIENDS_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.GROUP_CHAT_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.HOME_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.LOGIN_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.PROFILE_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.REGISTER_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.SEARCHUSER_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.WELCOME_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.routes.CreateEventRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.EditEventRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.EditProfileRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.EventCenterRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.EventRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.FriendsRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.GroupChatRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.HomeRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.LoginRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.ProfileRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.RegisterRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.SearchUserRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.WelcomeRoute
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModelFactory
import java.util.Locale

object Destinations {
    const val WELCOME_ROUTE = "welcome"
    const val LOGIN_ROUTE = "login"
    const val REGISTER_ROUTE = "register"
    const val HOME_ROUTE = "home"
    const val EVENT_ROUTE = "event"
    const val EVENTCENTER_ROUTE = "eventcenter"
    const val PROFILE_ROUTE = "profile"
    const val EDITPROFILE_ROUTE = "editprofile"
    const val FRIENDS_ROUTE = "friends"
    const val CREATEEVENT_ROUTE = "createevent"
    const val SEARCHUSER_ROUTE = "searchuser"
    const val EDITEVENT_ROUTE = "editevent"
    const val GROUP_CHAT_ROUTE = "chat"
}

var mainViewModel: MainViewModel = MainViewModel()

@Composable
fun EventSphereNavHost(
    navController: NavHostController = rememberNavController(),
    setLanguage: (locale: Locale) -> Unit,
    setTheme: (Boolean) -> Unit
) {
    var userToNavigate: User? = null
    var eventToNavigate: Event? = null

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
                onNavigationToEvent = { event: Event ->
                    eventToNavigate = event
                    navController.navigate("$EVENT_ROUTE/$HOME_ROUTE")
                },
                onNavigationToBack = {
                    navController.navigate(WELCOME_ROUTE)
                },
                mainViewModel = mainViewModel,
                onNavigationToGroupChat = {
                    navController.navigate(GROUP_CHAT_ROUTE)
                }
            )
        }

        composable("$EVENT_ROUTE/{route}") {backStackEntry ->
            val route = backStackEntry.arguments?.getString("route")
            EventRoute(
                event = eventToNavigate!!,
                mainViewModel = mainViewModel,
                onNavigationBack = {
                    if (route == "home")
                        navController.navigate(HOME_ROUTE)
                    else
                        navController.navigate(EVENTCENTER_ROUTE)
                },
                toEdit = { event ->
                    eventToNavigate = event
                    if (route == "home") {
                        navController.navigate("$EDITEVENT_ROUTE/$HOME_ROUTE")
                    } else {
                        navController.navigate("$EDITEVENT_ROUTE/$EVENTCENTER_ROUTE")
                    }

                }
            )
        }

        composable("$EDITEVENT_ROUTE/{route}") { backStackEntry ->
            val route = backStackEntry.arguments?.getString("route")
            EditEventRoute(
                event = eventToNavigate!!,
                mainViewModel = mainViewModel,
                toBack = {
                    if (route == "home")
                        navController.navigate(HOME_ROUTE)
                    else
                        navController.navigate(EVENTCENTER_ROUTE)
                },
            )
        }

        composable(EVENTCENTER_ROUTE) {
            EventCenterRoute(
                mainViewModel = mainViewModel,
                onNavigationBack = {
                    navController.navigate(PROFILE_ROUTE)
                },
                onNavigationToEvent = { event: Event ->
                    eventToNavigate = event
                    navController.navigate("$EVENT_ROUTE/$EVENTCENTER_ROUTE")
                },
                onNavigationToCreateEvent = {
                    navController.navigate(CREATEEVENT_ROUTE)
                }
            )
        }

        composable(PROFILE_ROUTE) {
            ProfileRoute(
                onNavigationToLogout = {
                    navController.navigate(WELCOME_ROUTE)
                },
                onNavigationToBack = {
                    navController.navigate(HOME_ROUTE)
                },
                onNavigationToEventCenter = {
                    navController.navigate(EVENTCENTER_ROUTE)
                },
                onNavigationToEditProfile = {
                    navController.navigate(EDITPROFILE_ROUTE)
                },
                onNavigationToFriendsScreen = { friend: User ->
                    userToNavigate = friend
                    navController.navigate(FRIENDS_ROUTE)
                },
                onNavigationToSearchUserScreen = {
                    navController.navigate(SEARCHUSER_ROUTE)
                },
                setLanguage = { setLanguage(it) },
                setTheme = { setTheme(it) },
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
                friend = userToNavigate!!,
                onNavigationToProfile = {
                    navController.navigate(PROFILE_ROUTE)
                },
                mainViewModel = mainViewModel
            )
        }

        composable(CREATEEVENT_ROUTE) {
            CreateEventRoute(
                onNavigationToBack = {
                    navController.navigate(EVENTCENTER_ROUTE)
                },
                mainViewModel = mainViewModel
            )
        }

        composable(SEARCHUSER_ROUTE) {
            SearchUserRoute(
                onNavigationToProfile = {
                    navController.navigate(PROFILE_ROUTE)
                },
                onNavigationToFriendsScreen = { friend: User ->
                    userToNavigate = friend
                    navController.navigate(FRIENDS_ROUTE)
                },
                mainViewModel = mainViewModel
            )
        }

        composable(GROUP_CHAT_ROUTE) {
            GroupChatRoute(
                onNavigationToBack = {
                    navController.navigate(HOME_ROUTE)
                },
                mainViewModel = mainViewModel,
            )
        }
    }
}
