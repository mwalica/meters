package ch.walica.meters.presentation.common

sealed class CommonAction {
    object OnBackArrowClick : CommonAction()
    data class OnAddMeterReadingClick(val meter: String) : CommonAction()

}