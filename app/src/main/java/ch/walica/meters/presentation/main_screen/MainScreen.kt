package ch.walica.meters.presentation.main_screen

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
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
            ScreenAppBar()
        }

    ) { paddingValues ->

        val averageBicycle = bicycleMeterReadings.value.mapIndexed { index, item ->
            if (index != 0) {
                item.reading - bicycleMeterReadings.value[index - 1].reading
            } else {
                item.reading
            }
        }.average()

        val cards = listOf<MeterCard>(
            MeterCard(
                name = stringResource(id = R.string.car),
                color= Green,
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
                route = Screen.BicycleScreen.route,
                img = R.drawable.water_drop_300
            ),
            MeterCard(
                name = stringResource(R.string.gas),
                color = Orange,
                route = Screen.BicycleScreen.route,
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
fun ScreenAppBar() {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.main_screen_title))
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
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
                    color = Blue
                )
                Text(
                    text = buildAnnotatedString {
                        append("śr. ")
                        withStyle(
                            style = SpanStyle(
                                color = DarkGrey,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(meter.avg.toString())
                        }
                        append(" km")
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