package ch.walica.meters.presentation.main_screen

import android.app.Activity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.walica.meters.R
import ch.walica.meters.domain.model.MeterCard
import ch.walica.meters.navigation.Screen
import ch.walica.meters.ui.theme.*
import ch.walica.meters.util.UiEvent
import kotlin.math.roundToInt

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {

    val bicycleMeterReadings =
        viewModel.bicycleMeterReadings.collectAsState(initial = emptyList())
    val gasMeterReadings =
        viewModel.gasMeterReadings.collectAsState(initial = emptyList())

    val waterMeterReadings =
        viewModel.waterMeterReadings.collectAsState(initial = emptyList())

    val activity = LocalContext.current as? Activity


    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            ScreenAppBar(activity)
        },


        ) { paddingValues ->

        val averageBicycle = bicycleMeterReadings.value.mapIndexed { index, item ->
            if (index != bicycleMeterReadings.value.lastIndex) {
                item.reading - bicycleMeterReadings.value[index + 1].reading
            } else {
                item.reading
            }
        }.average()

        val averageGas = gasMeterReadings.value.mapIndexed { index, item ->
            if (index != gasMeterReadings.value.lastIndex) {
                item.reading - gasMeterReadings.value[index + 1].reading
            } else {
                item.reading
            }
        }.average()

        val averageWater = waterMeterReadings.value.mapIndexed { index, item ->
            if (index != waterMeterReadings.value.lastIndex) {
                item.reading - waterMeterReadings.value[index + 1].reading
            } else {
                item.reading
            }
        }.average()

        val cards = listOf<MeterCard>(
            MeterCard(
                name = stringResource(id = R.string.car),
                color = Green,
                route = Screen.BicycleScreen.route,
                img = R.drawable.car_300
            ),
            MeterCard(
                name = stringResource(R.string.bicycle),
                route = Screen.BicycleScreen.route,
                avg = if (averageBicycle.isNaN()) 0 else averageBicycle.roundToInt(),
                img = R.drawable.pedal_bike_300
            ),
            MeterCard(
                name = stringResource(R.string.water),
                color = LightBlue,
                route = Screen.WaterScreen.route,
                avg = if (averageWater.isNaN()) 0 else averageWater.roundToInt(),
                unit = "m3",
                img = R.drawable.water_drop_300
            ),
            MeterCard(
                name = stringResource(R.string.gas),
                color = Orange,
                route = Screen.GasScreen.route,
                avg = if (averageGas.isNaN()) 0 else averageGas.roundToInt(),
                unit = "m3",
                img = R.drawable.gas_300
            ),
            MeterCard(
                name = stringResource(R.string.electricity),
                color = Violet,
                route = Screen.BicycleScreen.route,
                img = R.drawable.electrical_services_300
            ),
        )

        Column(modifier = modifier.padding(paddingValues)) {
            LazyColumn() {
                items(cards) { meter ->
                    MeterItem(meter) {
                        viewModel.onAction(MainAction.OnMeterClick(meter.route))
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenAppBar(activity: Activity?) {

    var showMenu by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.main_screen_title),
                color = if (isSystemInDarkTheme()) LightGray else DarkGrey
            )
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        actions = {
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = "drop down menu"
                )
            }
            DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                DropdownMenuItem(onClick = { activity?.finish() }) {
                    Text(
                        text = stringResource(R.string.close),
                        color = if (isSystemInDarkTheme()) LightGrey else DarkGrey
                    )
                }
            }
        }
    )
}

@Composable
fun MeterItem(meter: MeterCard, clickHandler: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
        shape = RoundedCornerShape(16.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = meter.name,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.primary
                )
                Text(
                    text = buildAnnotatedString {
                        append("śr. ")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colors.secondary,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(meter.avg.toString())
                        }
                        append(" ${meter.unit}")
                    },
                    style = MaterialTheme.typography.subtitle2
                )

            }
            Column {
                Card(
                    shape = CircleShape,
                    elevation = 0.dp,
                    backgroundColor = meter.color
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clickable { clickHandler() },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = meter.img),
                            contentDescription = meter.name,
                            modifier = Modifier.size(30.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }


            }
        }
    }
}