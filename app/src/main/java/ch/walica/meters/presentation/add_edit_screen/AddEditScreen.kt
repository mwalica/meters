package ch.walica.meters.presentation.add_edit_screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.walica.meters.R
import ch.walica.meters.ui.theme.Blue
import ch.walica.meters.ui.theme.Grey
import ch.walica.meters.ui.theme.LightGrey
import ch.walica.meters.util.UiEvent
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
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
                .ofPattern("dd MMMM yyyy")
                .format(viewModel.pickedDate)
        }
    }

    val context = LocalContext.current



    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopUpBackStack()
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
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
                               elevation = 0.dp
                           )
            }
        },
        topBar = {
            ScreenAppBar(
                title = viewModel.meterType ?: "error",
                onBackArrow = { viewModel.onAction(AddEditAction.OnBackArrowClick) }
            )
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
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    TextField(
                        modifier = Modifier.padding(bottom = 16.dp),
                        value = viewModel.readingValue,
                        onValueChange = { str ->
                            viewModel.onAction(
                                AddEditAction.OnReadingValueChange(
                                    str
                                )
                            )
                        },
                        label = {
                            Text(text = stringResource(R.string.text_field_value))
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent
                        )
                    )
                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.subtitle2
                    )
                    Button(
                        shape = CircleShape,
                        elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent
                        ),
                        border = BorderStroke(1.dp, LightGrey),
                        contentPadding = PaddingValues(16.dp, 8.dp),
                        onClick = { dateDialogState.show() }) {
                        Text(
                            text = stringResource(R.string.select_date),
                            color = Blue
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        shape = CircleShape,
                        elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent
                        ),
                        border = BorderStroke(1.dp, LightGrey),
                        contentPadding = PaddingValues(16.dp, 8.dp),
                        onClick = { viewModel.onAction(AddEditAction.OnAddEditMeterReadingClick) }) {
                        Text(
                            text = stringResource(R.string.add_value),
                            color = Blue
                        )
                    }
                }
            }


        }
    }

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(stringResource(R.string.select))
            negativeButton(stringResource(R.string.cancel))
        }
    ) {
        viewModel.pickedDate?.let {
            datepicker(
                initialDate = it.toLocalDate(),
                title = stringResource(id = R.string.select_date)
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
                    imageVector = Icons.Rounded.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.back_arrow)
                )
            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}