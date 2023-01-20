package ch.walica.meters.util

sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
    object PopBackStack : UiEvent()
    data class ShowSnackBar(val message: UiText, val action: UiText? = null): UiEvent()
}
