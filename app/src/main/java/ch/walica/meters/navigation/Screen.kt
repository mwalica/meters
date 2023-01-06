package ch.walica.meters.navigation

sealed class Screen(val route: String) {
    object MainScreen : Screen("main")
    object AddEditScreen : Screen("add_edit")
    object ListScreen : Screen("bicycle")
    object WaterScreen : Screen("water")
    object ElectricityScreen : Screen("electricity")
    object GasScreen : Screen("gas")
}
