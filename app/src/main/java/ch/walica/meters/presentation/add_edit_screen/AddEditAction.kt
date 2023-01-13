package ch.walica.meters.presentation.add_edit_screen

import java.time.LocalDate


sealed class AddEditAction {
    object OnBackArrowClick : AddEditAction()
    data class OnReadingValueChange(val readingValue: String) : AddEditAction()
    data class OnPickedDateChanged(val pickedDate: LocalDate) : AddEditAction()
    object OnAddEditMeterReadingClick : AddEditAction()
}