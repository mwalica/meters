package ch.walica.meters.presentation.main_screen


sealed class MainAction {
    data class OnMeterClick(val selectedMeter: String) : MainAction()
}
