package ch.walica.meters.presentation.gas_screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.walica.meters.R
import ch.walica.meters.presentation.gas_screen.components.MeterReadingItem
import ch.walica.meters.presentation.common.CommonAction
import ch.walica.meters.ui.theme.DarkGrey
import ch.walica.meters.ui.theme.LightBlue
import ch.walica.meters.util.UiEvent

@Composable
fun GasScreen(
    modifier: Modifier = Modifier,
    viewModel: GasViewModel = hiltViewModel(),
    onPopUpBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
) {

    val title = stringResource(id = R.string.gas)
    val meterReadings = viewModel.meterReadings.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect() { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopUpBackStack()
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                        actionLabel = event.action?.asString(context)
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onAction(CommonAction.OnUndoMeterReadingClick)
                    }
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { snackBarHostState ->
            SnackbarHost(hostState = snackBarHostState) {
                Snackbar(
                    snackbarData = it,
                    backgroundColor = Color.White,
                    elevation = 0.dp,
                )
            }
        },
        topBar = {
            ScreenAppBar(
                title = title,
                onBackArrow = { viewModel.onAction(CommonAction.OnBackArrowClick) })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onAction(CommonAction.OnAddMeterReadingClick(title))
                },
                backgroundColor = LightBlue,
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_meter_reading),
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        Column(modifier = modifier
            .padding(paddingValues)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (meterReadings.value.isNotEmpty()){
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
                                if (index != meterReadings.value.lastIndex) meterReadings.value[index].reading - meterReadings.value[index + 1].reading else meterReadings.value[index].reading
                            MeterReadingItem(meterReading = meterReading, usage) {
                                viewModel.onAction(
                                    CommonAction.OnDeleteMeterReadingClick(
                                        meterReading
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun ScreenAppBar(title: String, onBackArrow: () -> Unit) {
    TopAppBar(
        title = { Text(text = title,
            color = if(isSystemInDarkTheme()) Color.LightGray else DarkGrey
        ) },
        navigationIcon = {
            IconButton(onClick = { onBackArrow() }) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.back_arrow_description)
                )
            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}

