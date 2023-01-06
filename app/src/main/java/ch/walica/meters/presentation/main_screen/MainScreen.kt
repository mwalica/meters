package ch.walica.meters.presentation.main_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.walica.meters.R
import ch.walica.meters.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val cards = listOf<String>("Rower", "Woda", "Elektryczność", "Gaz")

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
            AppBar()
        }

    ) { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
            Text(text = "MainScreen")
            LazyColumn() {
                items(cards) {meter ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable {
                                viewModel.onAction(MainAction.OnMeterClick(meter))
                            }
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(text = meter)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppBar() {
    TopAppBar() {
        Text(text = stringResource(R.string.main_screen_title))
    }
}