package ch.walica.meters.presentation.main_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.walica.meters.navigation.Screen
import ch.walica.meters.use_case.GetMetersReadingFromType
import ch.walica.meters.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getMetersReadingFromType: GetMetersReadingFromType
) : ViewModel() {

    val bicycleMeterReadings = getMetersReadingFromType("Rower")
    val gasMeterReadings = getMetersReadingFromType("Gaz")

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: MainAction) {
        when (action) {
            is MainAction.OnMeterClick -> {
                sendUiEvent(UiEvent.Navigate(action.route))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}