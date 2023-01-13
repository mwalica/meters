package ch.walica.meters.presentation.main_screen


sealed class MainAction {
    data class OnMeterClick(val route: String) : MainAction()
}
