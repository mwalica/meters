package ch.walica.meters.presentation.water_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.walica.meters.R
import ch.walica.meters.domain.model.MeterReading
import ch.walica.meters.navigation.Screen
import ch.walica.meters.presentation.common.CommonAction
import ch.walica.meters.use_case.DeleteMeterReading
import ch.walica.meters.use_case.GetMetersReadingFromType
import ch.walica.meters.use_case.InsertMeterReading
import ch.walica.meters.util.UiEvent
import ch.walica.meters.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaterViewModel @Inject constructor(
    getMetersReadingFromType: GetMetersReadingFromType,
    private val deleteMeterReading: DeleteMeterReading,
    private val insertMeterReading: InsertMeterReading
) : ViewModel() {

    val meterReadings = getMetersReadingFromType("Woda")
    private var deletedMeterReading: MeterReading? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: CommonAction) {
        when (action) {
            is CommonAction.OnBackArrowClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is CommonAction.OnAddMeterReadingClick -> {
                sendUiEvent(UiEvent.Navigate(Screen.AddEditScreen.route + "?meter=${action.meter}"))
            }
            is CommonAction.OnDeleteMeterReadingClick -> {
                viewModelScope.launch {
                    deletedMeterReading = action.meter
                    deleteMeterReading(deletedMeterReading!!)
                    sendUiEvent(
                        UiEvent.ShowSnackBar(
                            message = UiText.StringResource(R.string.delete_meter_reading_msg),
                            action = UiText.StringResource(R.string.undo)
                        )
                    )

                }
            }
            is CommonAction.OnUndoMeterReadingClick -> {
                deletedMeterReading?.let {
                    viewModelScope.launch {
                        insertMeterReading(it)
                    }
                }
            }
            else -> Unit
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}