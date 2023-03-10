package ch.walica.meters.presentation.add_edit_screen

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.walica.meters.R
import ch.walica.meters.ui.theme.DarkGrey
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
    val activity = LocalContext.current as? Activity



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
                    elevation = 0.dp
                )
            }
        },
        topBar = {
            ScreenAppBar(
                title = viewModel.meterType ?: "error",
                onBackArrow = { viewModel.onAction(AddEditAction.OnBackArrowClick) },
                activity = activity
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
                            backgroundColor = Color.Transparent,
                            textColor = if(isSystemInDarkTheme()) LightGrey else DarkGrey
                        ),

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
                        border = BorderStroke(
                            1.dp,
                            if (isSystemInDarkTheme()) DarkGrey else LightGrey
                        ),
                        contentPadding = PaddingValues(16.dp, 8.dp),
                        onClick = { dateDialogState.show() }) {
                        Text(
                            text = stringResource(R.string.select_date),
                            color = MaterialTheme.colors.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        shape = CircleShape,
                        elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (isSystemInDarkTheme()) DarkGrey else LightGrey
                        ),
                        contentPadding = PaddingValues(16.dp, 8.dp),
                        onClick = { viewModel.onAction(AddEditAction.OnAddEditMeterReadingClick) }) {
                        Text(
                            text = stringResource(R.string.add_value),
                            color = MaterialTheme.colors.primary
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
fun ScreenAppBar(title: String, onBackArrow: () -> Unit, activity: Activity?) {
    var showMenu by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = {
            Text(
                text = title,
                color = if (isSystemInDarkTheme()) Color.LightGray else DarkGrey
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackArrow() }) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.back_arrow)
                )
            }
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
                        color = if(isSystemInDarkTheme()) LightGrey else DarkGrey
                    )
                }
            }
        }
    )
}