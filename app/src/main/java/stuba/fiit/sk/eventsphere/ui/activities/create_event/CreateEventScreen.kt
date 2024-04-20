package stuba.fiit.sk.eventsphere.ui.activities.create_event

import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.model.DateInput
import stuba.fiit.sk.eventsphere.model.FriendPerformer
import stuba.fiit.sk.eventsphere.model.observeLiveData
import stuba.fiit.sk.eventsphere.ui.activities.profile.FriendBox
import stuba.fiit.sk.eventsphere.ui.components.AlertDialogComponent
import stuba.fiit.sk.eventsphere.ui.components.ButtonComponent
import stuba.fiit.sk.eventsphere.ui.components.CategoryBox
import stuba.fiit.sk.eventsphere.ui.components.InputFieldComponent
import stuba.fiit.sk.eventsphere.ui.components.MapLocationPicker
import stuba.fiit.sk.eventsphere.ui.components.SmallButtonComponent
import stuba.fiit.sk.eventsphere.ui.theme.LightColorScheme
import stuba.fiit.sk.eventsphere.ui.theme.buttonStyle
import stuba.fiit.sk.eventsphere.ui.theme.labelStyle
import stuba.fiit.sk.eventsphere.ui.theme.paragraph
import stuba.fiit.sk.eventsphere.viewmodel.CreateEventViewModel
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel


@Preview(showBackground = true)
@Composable
fun Preview () {
    CreateEventScreen(back = {}, viewModel = MainViewModel(), createEventViewModel = CreateEventViewModel(
        MainViewModel()
    ))
}


@Composable
fun CreateEventScreen(
    back: () -> Unit,
    viewModel: MainViewModel,
    createEventViewModel: CreateEventViewModel
) {
    var isMapSelected by remember { mutableStateOf(false)}

    if (isMapSelected) {
        val properties by remember { mutableStateOf(MapProperties(mapType = MapType.TERRAIN)) }
        val uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = true)) }

        MapLocationPicker(
            properties = properties,
            uiSettings = uiSettings,
            userLocationInput = LatLng(0.0, 0.0),
            isForPicking = true,
            onAdd = { input ->
                createEventViewModel.updateLocation(input)
                isMapSelected = false
            }
        )
    } else {
        Column (
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            CreateEventTopBar(
                back = back,
                createEventViewModel = createEventViewModel
            )

            Spacer(modifier = Modifier.height(5.dp))

            Column (
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EventDetailInput (
                    createEventViewModel = createEventViewModel,
                    onMapShow = { isMapSelected = true }
                )
                Spacer (
                    modifier = Modifier
                        .height(10.dp)
                )
                PerformersRow (
                    createEventViewModel = createEventViewModel
                )
            }
        }
    }
}


@Composable
fun PerformersRow(
    createEventViewModel: CreateEventViewModel
) {
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Performers",
            style = buttonStyle,
            color = LightColorScheme.onBackground,
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier
                .height(20.dp)
        )


        val performerScroll = rememberScrollState()
        var isSelectedPerformer by remember { mutableStateOf(false) }
        var selectedPerformer by remember { mutableStateOf<FriendPerformer?>(null) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .horizontalScroll(performerScroll),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            createEventViewModel.event.value?.performers?.forEach { performer ->
                FriendBox(
                    firstname = performer.firstname ?: "",
                    lastname = performer.lastname ?: "",
                    onClick = {
                        if (isSelectedPerformer && selectedPerformer == performer) {
                            isSelectedPerformer = false
                            selectedPerformer = null
                        } else if (isSelectedPerformer && selectedPerformer != performer) {
                            isSelectedPerformer = true
                            selectedPerformer = performer
                        } else {
                            isSelectedPerformer = true
                            selectedPerformer = performer
                        }
                    },
                    id = performer.id!!,
                    image = performer.profile_picture
                )
            }

        }

        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isSelectedPerformer) {
                SmallButtonComponent(
                    text = "Remove",
                    isSelected = false,
                    onClick = {
                        createEventViewModel.removePerformer(selectedPerformer)
                        isSelectedPerformer = false
                    },
                )
            }
        }


        var addPerformer by remember { mutableStateOf(false) }

        Spacer(
            modifier = Modifier
                .height(20.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 0.dp)
        ) {
            ButtonComponent(
                onClick = { addPerformer = true },
                fillColor = LightColorScheme.primary,
                textColor = LightColorScheme.background,
                text = "Add performer",
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )

            if (addPerformer) {
                val friendScrollState = rememberScrollState()

                if (createEventViewModel.friendsList.isEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "No friends to add :(",
                        style = labelStyle,
                        textAlign = TextAlign.Center
                    )
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(100.dp)
                            .verticalScroll(friendScrollState),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        createEventViewModel.friendsList.forEach { friend ->
                            FriendBox(
                                firstname = friend.firstname ?: "Firstname",
                                lastname = friend.lastname ?: "Lastname",
                                onClick = {
                                    createEventViewModel.addPerformer(friend)
                                    addPerformer = false
                                },
                                id = friend.id!!,
                                image = friend.profile_picture
                            )
                            Spacer (
                                modifier = Modifier
                                    .width(20.dp)
                            )

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventDetailInput (
    createEventViewModel: CreateEventViewModel,
    onMapShow: () -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputFieldComponent(
            label = "Title",
            text = createEventViewModel.event.value?.title.toString(),
            onUpdate = createEventViewModel::updateTitle,
            keyboardType = KeyboardType.Text,
            onCheck = null,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(
            modifier = Modifier
                .height(10.dp)
        )

        InputFieldComponent(
            label = "Description",
            text = createEventViewModel.event.value?.description.toString(),
            onUpdate = createEventViewModel::updateDescription,
            keyboardType = KeyboardType.Text,
            onCheck = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
        )

        Spacer (
            modifier = Modifier.height(10.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {

            SelectBoxesView (
                onUpdate = {
                    createEventViewModel.onUpdateCategory(it)
                }

            )

        }

        Spacer(
            modifier = Modifier
                .height(10.dp)
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(LightColorScheme.primary),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                var estimatedEnd by remember { mutableStateOf(createEventViewModel.event.value?.estimated_end) }
                val context = LocalContext.current


                val datePicker = android.app.DatePickerDialog(
                    context,
                    { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                        estimatedEnd?.let {
                            it.day = selectedDayOfMonth
                            it.month = selectedMonth
                            it.year = selectedYear
                        }
                    },
                    createEventViewModel.event.value?.estimated_end?.year ?: 0, createEventViewModel.event.value?.estimated_end?.month ?: 0, createEventViewModel.event.value?.estimated_end?.day ?: 0
                )

                val timePicker = TimePickerDialog(
                    context,
                    { _, selectedHour: Int, selectedMinute: Int ->
                        estimatedEnd?.let {
                            it.hour = selectedHour
                            it.minutes = selectedMinute
                        }
                    },
                    createEventViewModel.event.value?.estimated_end?.hour ?: 0, createEventViewModel.event.value?.estimated_end?.minutes ?: 0, false
                )

                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                timePicker.show()
                                datePicker.show()
                            }
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon),
                        contentDescription = "datepicker"
                    )
                }

            }
            Column (
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                SmallButtonComponent(
                    text = "Location",
                    isSelected = false,
                    onClick = { onMapShow() }
                )
                Text (
                    text = createEventViewModel.event.value?.location?.address ?: "Address",
                    style = paragraph,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun SelectBoxesView(
    onUpdate: (id: Int) -> Unit
) {
    var isSelectedEducation by remember { mutableStateOf(false) }
    var isSelectedMusic by remember { mutableStateOf(false) }
    var isSelectedFood by remember { mutableStateOf(false) }
    var isSelectedArt by remember { mutableStateOf(false) }
    var isSelectedSport by remember { mutableStateOf(false) }

    Box (
        modifier = Modifier
            .width(48.dp)
            .height(48.dp)
            .clip(
                RoundedCornerShape(15.dp)
            )
            .border(
                3.dp,
                if (isSelectedEducation) LightColorScheme.primary else LightColorScheme.primaryContainer,
                RoundedCornerShape(15.dp)
            )
            .background(LightColorScheme.background)
            .padding(2.dp)
            .clickable(
                onClick = {
                    isSelectedEducation = !isSelectedEducation
                    isSelectedMusic = false
                    isSelectedFood = false
                    isSelectedArt = false
                    isSelectedSport = false
                    onUpdate(1)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.book_icon
            ),
            contentDescription = "icon",
            contentScale = ContentScale.Inside,
            colorFilter = ColorFilter.tint(if (isSelectedEducation) LightColorScheme.primary else LightColorScheme.primaryContainer)
        )
    }

    Box (
        modifier = Modifier
            .width(48.dp)
            .height(48.dp)
            .clip(
                RoundedCornerShape(15.dp)
            )
            .border(
                3.dp,
                if (isSelectedMusic) LightColorScheme.primary else LightColorScheme.primaryContainer,
                RoundedCornerShape(15.dp)
            )
            .background(LightColorScheme.background)
            .padding(2.dp)
            .clickable(
                onClick = {
                    isSelectedMusic = !isSelectedMusic
                    isSelectedEducation = false
                    isSelectedFood = false
                    isSelectedArt = false
                    isSelectedSport = false
                    onUpdate(2)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.music_icon
            ),
            contentDescription = "icon",
            contentScale = ContentScale.Inside,
            colorFilter = ColorFilter.tint(if (isSelectedMusic) LightColorScheme.primary else LightColorScheme.primaryContainer)
        )
    }


    Box (
        modifier = Modifier
            .width(48.dp)
            .height(48.dp)
            .clip(
                RoundedCornerShape(15.dp)
            )
            .border(
                3.dp,
                if (isSelectedFood) LightColorScheme.primary else LightColorScheme.primaryContainer,
                RoundedCornerShape(15.dp)
            )
            .background(LightColorScheme.background)
            .padding(2.dp)
            .clickable(
                onClick = {
                    isSelectedFood = !isSelectedFood
                    isSelectedEducation = false
                    isSelectedMusic = false
                    isSelectedArt = false
                    isSelectedSport = false
                    onUpdate(3)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.burger_icon
            ),
            contentDescription = "icon",
            contentScale = ContentScale.Inside,
            colorFilter = ColorFilter.tint(if (isSelectedFood) LightColorScheme.primary else LightColorScheme.primaryContainer)
        )
    }

    Box (
        modifier = Modifier
            .width(48.dp)
            .height(48.dp)
            .clip(
                RoundedCornerShape(15.dp)
            )
            .border(
                3.dp,
                if (isSelectedArt) LightColorScheme.primary else LightColorScheme.primaryContainer,
                RoundedCornerShape(15.dp)
            )
            .background(LightColorScheme.background)
            .padding(2.dp)
            .clickable(
                onClick = {
                    isSelectedArt = !isSelectedArt
                    isSelectedEducation = false
                    isSelectedFood = false
                    isSelectedMusic = false
                    isSelectedSport = false
                    onUpdate(4)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.brush_icon
            ),
            contentDescription = "icon",
            contentScale = ContentScale.Inside,
            colorFilter = ColorFilter.tint(if (isSelectedArt) LightColorScheme.primary else LightColorScheme.primaryContainer)
        )
    }


    Box (
        modifier = Modifier
            .width(48.dp)
            .height(48.dp)
            .clip(
                RoundedCornerShape(15.dp)
            )
            .border(
                3.dp,
                if (isSelectedSport) LightColorScheme.primary else LightColorScheme.primaryContainer,
                RoundedCornerShape(15.dp)
            )
            .background(LightColorScheme.background)
            .padding(2.dp)
            .clickable(
                onClick = {
                    isSelectedSport = !isSelectedSport
                    isSelectedEducation = false
                    isSelectedMusic = false
                    isSelectedArt = false
                    isSelectedArt = false
                    onUpdate(5)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.dribbble_icon
            ),
            contentDescription = "icon",
            contentScale = ContentScale.Inside,
            colorFilter = ColorFilter.tint(if (isSelectedSport) LightColorScheme.primary else LightColorScheme.primaryContainer)
        )
    }

}

@Composable
fun CreateEventTopBar (
    back: () -> Unit,
    createEventViewModel: CreateEventViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.top_bar),
            contentDescription = "welcome_background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .padding(10.dp)
                .matchParentSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = back,
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "Back"
                )
            }


            val openSaveDialog = remember { mutableStateOf(false) }
            var openErrorDialog = remember { mutableStateOf(false) }
            var errorMessage = remember { mutableStateOf("") }

            SmallButtonComponent(
                onClick = {
                    createEventViewModel.viewModelScope.launch {
                        val (result, message) = createEventViewModel.createEvent()
                        openErrorDialog.value = !result
                        errorMessage.value = message
                    }
                    if (!openErrorDialog.value) openSaveDialog.value = true
                },
                text = "Create",
                isSelected = false
            )
            if(openSaveDialog.value) {
                AlertDialogComponent(
                    onDismissRequest = { },
                    onConfirmation = {
                        openSaveDialog.value = false
                        back()
                    },
                    dialogTitle = "Event created",
                    dialogText = "Your event was created",
                    onDismissText = "",
                    onConfirmText = "To event center"
                )
            } else if (openErrorDialog.value) {
                AlertDialogComponent(
                    onDismissRequest = { },
                    onConfirmation = {
                        openErrorDialog.value = false
                    },
                    dialogTitle = "Error creating event",
                    dialogText = errorMessage.value,
                    onDismissText = "",
                    onConfirmText = "Close"
                )
            }

        }
    }
}

