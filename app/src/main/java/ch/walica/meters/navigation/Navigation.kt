package ch.walica.meters.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ch.walica.meters.presentation.add_edit_screen.AddEditScreen
import ch.walica.meters.presentation.bicycle_screen.BicycleScreen
import ch.walica.meters.presentation.gas_screen.GasScreen
import ch.walica.meters.presentation.main_screen.MainScreen
import ch.walica.meters.presentation.water_screen.WaterScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(onNavigate = {
                navController.navigate(it.route)
            })
        }

        composable(route = Screen.BicycleScreen.route) {
            BicycleScreen(
                onPopUpBackStack = { navController.popBackStack() },
                onNavigate = {
                    navController.navigate(it.route)
                })
        }

        composable(route = Screen.GasScreen.route) {
            GasScreen(
                onPopUpBackStack = { navController.popBackStack() },
                onNavigate = {
                    navController.navigate(it.route)
                })
        }

        composable(route = Screen.WaterScreen.route) {
            WaterScreen(
                onPopUpBackStack = { navController.popBackStack() },
                onNavigate = {
                    navController.navigate(it.route)
                })
        }

        composable(route = Screen.AddEditScreen.route + "?meter={meter}&meterReadingId={meterReadingId}",
            arguments = listOf(
                navArgument(
                    name = "meter",
                    builder = {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                ),
                navArgument(
                    name = "meterReadingId",
                    builder = {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            )
        ) {
            AddEditScreen(
                onPopUpBackStack = { navController.popBackStack() }
            )
        }
    }
}