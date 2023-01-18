package ch.walica.meters.presentation.common

import ch.walica.meters.domain.model.MeterReading

sealed class CommonAction {
    object OnBackArrowClick : CommonAction()
    data class OnAddMeterReadingClick(val meter: String) : CommonAction()
    data class OnDeleteMeterReadingClick(val meter: MeterReading) : CommonAction()
    object OnUndoMeterReadingClick : CommonAction()

}