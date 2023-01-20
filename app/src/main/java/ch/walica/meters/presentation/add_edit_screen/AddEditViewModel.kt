package ch.walica.meters.presentation.add_edit_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.walica.meters.R
import ch.walica.meters.domain.model.MeterReading
import ch.walica.meters.use_case.InsertMeterReading
import ch.walica.meters.util.UiEvent
import ch.walica.meters.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val insertMeterReading: InsertMeterReading,
    savedStateHandler: SavedStateHandle
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var readingValue by mutableStateOf("")
        private set

    var pickedDate: ZonedDateTime? by mutableStateOf(ZonedDateTime.now())
        private set

    var meterType: String? = null

    init {
        meterType = savedStateHandler.get<String>("meter")
        val meterReadingId = savedStateHandler.get<Int>("meterReadingId")
        if (meterReadingId != -1) {
            viewModelScope.launch {

            }
        }
    }

    fun onAction(action: AddEditAction) {
        when (action) {
            is AddEditAction.OnBackArrowClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is AddEditAction.OnReadingValueChange -> {
                readingValue = action.readingValue
            }
            is AddEditAction.OnPickedDateChanged -> {
                pickedDate = action.pickedDate.atStartOfDay(ZoneId.systemDefault());
            }
            is AddEditAction.OnAddEditMeterReadingClick -> {
                viewModelScope.launch {
                    if (readingValue.isBlank() || meterType.isNullOrBlank() || pickedDate == null) {
                        Log.d("my_log", "snackbar: run")
                        sendUiEvent(UiEvent.ShowSnackBar(message = UiText.StringResource(R.string.text_field_error)))
                        return@launch
                    }

                    insertMeterReading(MeterReading(
                        type = meterType!!,
                        reading = readingValue.toInt(),
                        date = pickedDate!!

                    ))
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}