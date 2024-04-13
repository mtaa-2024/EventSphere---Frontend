package stuba.fiit.sk.eventsphere.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.HOME_SCREEN
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.LOGIN_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.REGISTER_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.WELCOME_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.routes.HomeRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.LoginRoute
import stuba.fiit.sk.eventsphere.ui.navigation.routes.WelcomeRoute
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModelFactory

object Destinations {
    const val WELCOME_ROUTE = "welcome"
    const val LOGIN_ROUTE = "login"
    const val REGISTER_ROUTE = "register"
    const val HOME_SCREEN = "home"
}

@Composable
fun EventSphereNavHost(
    navController: NavHostController = rememberNavController(),
) {
    val mainViewModel: MainViewModel = viewModel(factory = MainViewModelFactory())

    NavHost(
        navController = navController,
        startDestination = WELCOME_ROUTE
    ) {
        composable(WELCOME_ROUTE) {
            WelcomeRoute(
                onNavigationToLogin = {
                    navController.navigate(LOGIN_ROUTE)
                },
                onNavigationToRegister = {
                    navController.navigate(REGISTER_ROUTE)
                },
                onNavigationToHome = {
                    navController.navigate(HOME_SCREEN)
                }
            )
        }
        composable(LOGIN_ROUTE) {
            LoginRoute(
                onNavigationToHome = {
                    navController.navigate(HOME_SCREEN)
                },
                onNavigationToBack = {
                    navController.navigate(WELCOME_ROUTE)
                },
                mainViewModel = mainViewModel
            )
        }
        composable(REGISTER_ROUTE) {
            /*RegisterRoute(

            )*/
        }

        composable(HOME_SCREEN) {
            HomeRoute(
                onNavigationToProfile = {
                    navController.navigate(HOME_SCREEN)
                },
                onNavigationToBack = {
                    navController.navigate(WELCOME_ROUTE)
                },
                mainViewModel = mainViewModel
            )
        }

    }
}
