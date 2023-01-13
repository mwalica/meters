package ch.walica.meters.presentation.bicycle_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ch.walica.meters.R
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
            Text(text = "Bicycle")
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