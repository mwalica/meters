package ch.walica.meters.presentation.list_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ch.walica.meters.R

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        AppBar(title = viewModel.selectedMeter)
    }) { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
            Text(text = "ListScreen")
        }

    }
}

@Composable
fun AppBar(title: String?) {
    TopAppBar() {
        Text(text = title ?: "nieznany")
    }
}