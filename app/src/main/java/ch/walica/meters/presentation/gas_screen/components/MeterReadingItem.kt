package ch.walica.meters.presentation.gas_screen.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ch.walica.meters.R
import ch.walica.meters.domain.model.MeterReading
import ch.walica.meters.ui.theme.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@Composable
fun MeterReadingItem(meterReading: MeterReading, usage: Int, onDblClick: () -> Unit) {
    Column(modifier = Modifier
        .padding(8.dp)
        .pointerInput(Unit) {
            detectTapGestures(
                onDoubleTap = { onDblClick() }
            )
        }) {
        Text(
            text = DateTimeFormatter
                .ofLocalizedDate(FormatStyle.MEDIUM)
                .format(meterReading.date),
            style = MaterialTheme.typography.subtitle1,
            color = if (isSystemInDarkTheme()) Red else DarkGrey,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Center
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.meter_status),
                    style = MaterialTheme.typography.subtitle2
                )
                Text(
                    text = "${meterReading.reading} m",
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.distance),
                    style = MaterialTheme.typography.subtitle2
                )
                Text(
                    text = "$usage m",
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Divider(color = if (isSystemInDarkTheme()) DarkGrey else LightGrey)
    }
}