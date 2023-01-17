package ch.walica.meters.presentation.bicycle_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.walica.meters.R
import ch.walica.meters.presentation.bicycle_screen.components.MeterReadingItem
import ch.walica.meters.presentation.common.CommonAction
import ch.walica.meters.util.UiEvent

@Composable
fun BicycleScreen(
    modifier: Modifier = Modifier,
    viewModel: BicycleViewModel = hiltViewModel(),
    onPopUpBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
) {

    val title = stringResource(id = R.string.bicycle)
    val meterReadings = viewModel.meterReadings.collectAsState(initial = emptyList())

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect() { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopUpBackStack()
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Scaffold(topBar = {
        ScreenAppBar(
            title = title,
            onBackArrow = { viewModel.onAction(CommonAction.OnBackArrowClick) })
    },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onAction(
                    CommonAction.OnAddMeterReadingClick(
                        title
                    )
                )
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_meter_reading)
                )
            }
        }
    ) { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                elevation = 0.dp,
                shape = RoundedCornerShape(24.dp)
            ) {
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    itemsIndexed(meterReadings.value) { index, meterReading ->
                        val usage =
                            if (index > 0) meterReadings.value[index].reading - meterReadings.value[index - 1].reading else meterReadings.value[index].reading
                        MeterReadingItem(meterReading = meterReading, usage);
                    }
                }
            }

        }

    }
}

@Composable
fun ScreenAppBar(title: String, onBackArrow: () -> Unit) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onBackArrow() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back_arrow_description)
                )
            }
        }
    )
}