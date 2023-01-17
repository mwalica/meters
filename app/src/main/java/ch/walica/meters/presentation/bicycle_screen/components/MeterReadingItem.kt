package ch.walica.meters.presentation.bicycle_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ch.walica.meters.R
import ch.walica.meters.domain.model.MeterReading
import ch.walica.meters.ui.theme.DarkGrey
import ch.walica.meters.ui.theme.LightGrey
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@Composable
fun MeterReadingItem(meterReading: MeterReading, usage: Int) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = DateTimeFormatter
                .ofLocalizedDate(FormatStyle.MEDIUM)
                .format(meterReading.date),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.meter_status),
                    style = MaterialTheme.typography.subtitle2
                )
                Text(text = "${meterReading.reading} km")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.distance),
                    style = MaterialTheme.typography.subtitle2
                )
                Text(text = "$usage km")
            }
        }
        Divider(color = LightGrey)

    }
}