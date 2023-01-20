package ch.walica.meters.util

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {
    class StringResource(
        @StringRes val resId: Int
    ) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is StringResource -> context.getString(resId)
        }
    }
}
