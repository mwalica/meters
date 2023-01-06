package ch.walica.meters.presentation.list_screen

import androidx.lifecycle.ViewModel
import ch.walica.meters.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


@HiltViewModel
class ListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val selectedMeter: String?
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        selectedMeter = savedStateHandle.get<String>("selectedMeter")
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}