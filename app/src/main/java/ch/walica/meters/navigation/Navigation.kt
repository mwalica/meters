package ch.walica.meters.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ch.walica.meters.navigation.Screen
import ch.walica.meters.presentation.list_screen.ListScreen
import ch.walica.meters.presentation.main_screen.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(onNavigate = {
                navController.navigate(it.route)
            })
        }

        composable(
            route = Screen.ListScreen.route + "?selectedMeter={selectedMeter}",
            arguments = listOf(
                navArgument(
                    name = "selectedMeter",
                    builder = {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            )
        ) {
            ListScreen()
        }
    }
}