package ch.walica.meters.presentation.add_edit_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.walica.meters.R
import ch.walica.meters.util.UiEvent
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AddEditScreen(
    modifier: Modifier = Modifier,
    viewModel: AddEditViewModel = hiltViewModel(),
    onPopUpBackStack: () -> Unit
) {

    val scaffoldState = rememberScaffoldState()
    val dateDialogState = rememberMaterialDialogState()
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMMM dd yyy")
                .format(viewModel.pickedDate)
        }
    }



    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopUpBackStack()
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ScreenAppBar(
                title = viewModel.meterType ?: "error",
                onBackArrow = { viewModel.onAction(AddEditAction.OnBackArrowClick) }
            )
        }
    ) { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
            TextField(
                value = viewModel.readingValue,
                onValueChange = { str -> viewModel.onAction(AddEditAction.OnReadingValueChange(str)) },
                label = {
                    Text(text = stringResource(R.string.text_field_value))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            Button(onClick = { dateDialogState.show() }) {
                Text(text = "Select date")
            }
            Text(text = formattedDate)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.onAction(AddEditAction.OnAddEditMeterReadingClick) }) {
                Text(text = stringResource(R.string.add_value))
            }
        }
    }

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton("Set date")
            negativeButton("Cancel")
        }
    ) {
        viewModel.pickedDate?.let {
            datepicker(
                initialDate = it.toLocalDate(),
                title = "Pick a date"
            ) {
                viewModel.onAction(AddEditAction.OnPickedDateChanged(it))
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
                    contentDescription = stringResource(R.string.back_arrow)
                )
            }
        }
    )
}