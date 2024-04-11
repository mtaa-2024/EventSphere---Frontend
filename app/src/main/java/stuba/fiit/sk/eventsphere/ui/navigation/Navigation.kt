package stuba.fiit.sk.eventsphere.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import stuba.fiit.sk.eventsphere.ui.activities.login.LoginRoute
import stuba.fiit.sk.eventsphere.ui.activities.login.LoginViewModel
import stuba.fiit.sk.eventsphere.ui.activities.login.LoginViewModelFactory
import stuba.fiit.sk.eventsphere.ui.activities.welcome.WelcomeRoute
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.LOGIN_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.REGISTER_ROUTE
import stuba.fiit.sk.eventsphere.ui.navigation.Destinations.WELCOME_ROUTE

object Destinations {
    const val WELCOME_ROUTE = "welcome"
    const val LOGIN_ROUTE = "login"
    const val REGISTER_ROUTE = "register"
}

@Composable
fun EventSphereNavHost(
    navController: NavHostController = rememberNavController(),
) {
    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory())


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
                    navController.navigate(WELCOME_ROUTE)
                }

            )
        }
        composable(LOGIN_ROUTE) {
            LoginRoute(
                onNavigationToHome = {
                    navController.navigate(WELCOME_ROUTE)
                },
                loginViewModel = loginViewModel
            )
        }
        composable(REGISTER_ROUTE) {
            /*LoginRoute(

            )*/
        }

    }
}
