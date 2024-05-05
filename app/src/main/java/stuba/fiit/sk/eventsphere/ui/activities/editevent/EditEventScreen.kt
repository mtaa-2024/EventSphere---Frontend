package stuba.fiit.sk.eventsphere.ui.activities.editevent

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.model.User
import stuba.fiit.sk.eventsphere.model.observeLiveData
import stuba.fiit.sk.eventsphere.ui.components.AlertDialogComponent
import stuba.fiit.sk.eventsphere.ui.components.ButtonComponent
import stuba.fiit.sk.eventsphere.ui.components.DateTimePicker
import stuba.fiit.sk.eventsphere.ui.components.FriendBox
import stuba.fiit.sk.eventsphere.ui.components.InputFieldComponent
import stuba.fiit.sk.eventsphere.ui.components.MapLocationPicker
import stuba.fiit.sk.eventsphere.ui.components.SmallButtonComponent
import stuba.fiit.sk.eventsphere.ui.navigation.mainViewModel
import stuba.fiit.sk.eventsphere.ui.theme.buttonStyle
import stuba.fiit.sk.eventsphere.ui.theme.labelStyle
import stuba.fiit.sk.eventsphere.ui.theme.paragraph
import stuba.fiit.sk.eventsphere.viewmodel.EditEventViewModel
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun EditEventScreen (
    mainViewModel: MainViewModel,
    back: () -> Unit,
    editEventViewModel: EditEventViewModel,
) {
    var isMapSelected by remember { mutableStateOf(false) }

    if (isMapSelected) {
        val properties by remember { mutableStateOf(MapProperties(mapType = MapType.TERRAIN)) }
        val uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = true)) }

        MapLocationPicker(
            properties = properties,
            uiSettings = uiSettings,
            latitude = 0.0,
            longitude = 0.0,
            isForPicking = true,
            onAdd = { input ->
                editEventViewModel.updateLocation(input)
                isMapSelected = false
            }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            EditCreateEventTopBar(
                toBack = back,
                editEventViewModel = editEventViewModel
            )

            Spacer(modifier = Modifier.height(5.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EditEventDetailInput(
                    editEventViewModel = editEventViewModel,
                    onMapShow = { isMapSelected = true }
                )
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )
                EditPerformersRow(
                    editEventViewModel = editEventViewModel
                )
            }
        }
    }
}


@Composable
fun EditPerformersRow(
    editEventViewModel: EditEventViewModel
) {
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Performers",
            style = buttonStyle,
            color = MaterialTheme.colorScheme.onBackground,
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
        var selectedPerformer by remember { mutableStateOf<User?>(null) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .horizontalScroll(performerScroll),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            editEventViewModel.event.performers?.forEach { performer ->
                val firstname = if (performer.firstname == null) "Firstname" else performer.firstname ?: ""
                val lastname = if (performer.lastname == null) "Lastname" else performer.lastname ?: ""
                FriendBox (
                    firstname = firstname,
                    lastname = lastname,
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
                    user = performer,
                    image = performer.profileImage
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
                        editEventViewModel.removePerformer(selectedPerformer!!)
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
                fillColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.background,
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
                if (mainViewModel.friendsData.value?.friends?.isEmpty() == true) {
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

                        mainViewModel.friendsData.value?.friends?.forEach { friend ->
                            val firstname = if (friend.firstname == null) "Firstname" else friend.firstname ?: ""
                            val lastname = if (friend.lastname == null) "Lastname" else friend.lastname ?: ""
                            FriendBox (
                                firstname = firstname,
                                lastname =lastname,
                                onClick = {
                                    editEventViewModel.addPerformer(friend)
                                    addPerformer = false
                                },
                                user = friend,
                                image = friend.profileImage
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
fun EditEventDetailInput (
    editEventViewModel: EditEventViewModel,
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
            text = editEventViewModel.eventData.value?.title!!,
            onUpdate = editEventViewModel::updateTitle,
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
            text = editEventViewModel.eventData.value?.description!!,
            onUpdate = editEventViewModel::updateDescription,
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
                    editEventViewModel.updateCategory(it)
                },
                editEventViewModel = editEventViewModel

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
                .background(MaterialTheme.colorScheme.primary),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            val dateTime = observeLiveData(liveData = editEventViewModel.eventData)
            DateTimePicker (
                editEventViewModel::updateDate,
                editEventViewModel::updateTime,
                date = dateTime?.estimatedEnd
            )
            Column (
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SmallButtonComponent(
                    text = stringResource(id = R.string.location),
                    isSelected = false,
                    onClick = { onMapShow() }
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text (
                    text = if(editEventViewModel.eventData.value?.location == null) stringResource(
                        id = R.string.address) else editEventViewModel.eventData.value?.location ?: "",
                    style = labelStyle,
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun SelectBoxesView(
    onUpdate: (id: Int) -> Unit,
    editEventViewModel: EditEventViewModel
) {
    var isSelectedEducation by remember { mutableStateOf(editEventViewModel.event.category == 1) }
    var isSelectedMusic by remember { mutableStateOf(editEventViewModel.event.category == 2) }
    var isSelectedFood by remember { mutableStateOf(editEventViewModel.event.category == 3) }
    var isSelectedArt by remember { mutableStateOf(editEventViewModel.event.category == 4) }
    var isSelectedSport by remember { mutableStateOf(editEventViewModel.event.category == 5) }

    Box (
        modifier = Modifier
            .width(48.dp)
            .height(48.dp)
            .clip(
                RoundedCornerShape(15.dp)
            )
            .border(
                3.dp,
                if (isSelectedEducation) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                RoundedCornerShape(15.dp)
            )
            .background(MaterialTheme.colorScheme.background)
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
            colorFilter = ColorFilter.tint(if (isSelectedEducation) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer)
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
                if (isSelectedMusic) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                RoundedCornerShape(15.dp)
            )
            .background(MaterialTheme.colorScheme.background)
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
            colorFilter = ColorFilter.tint(if (isSelectedMusic) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer)
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
                if (isSelectedFood) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                RoundedCornerShape(15.dp)
            )
            .background(MaterialTheme.colorScheme.background)
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
            colorFilter = ColorFilter.tint(if (isSelectedFood) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer)
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
                if (isSelectedArt) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                RoundedCornerShape(15.dp)
            )
            .background(MaterialTheme.colorScheme.background)
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
            colorFilter = ColorFilter.tint(if (isSelectedArt) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer)
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
                if (isSelectedSport) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                RoundedCornerShape(15.dp)
            )
            .background(MaterialTheme.colorScheme.background)
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
            colorFilter = ColorFilter.tint(if (isSelectedSport) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer)
        )
    }

}

@Composable
fun EditCreateEventTopBar (
    toBack: () -> Unit,
    editEventViewModel: EditEventViewModel
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
                onClick = toBack,
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
                    editEventViewModel.viewModelScope.launch {
                        val (result, message) = editEventViewModel.updateEvent()
                        openErrorDialog.value = !result
                        errorMessage.value = message
                    }
                    if (!openErrorDialog.value) openSaveDialog.value = true
                },
                text = "Update",
                isSelected = false
            )


            if(openSaveDialog.value) {
                AlertDialogComponent(
                    onDismissRequest = { },
                    onConfirmation = {
                        openSaveDialog.value = false
                        toBack()
                    },
                    dialogTitle = "Event updated",
                    dialogText = "Your event was updated",
                    onDismissText = "",
                    onConfirmText = "To event"
                )
            } else if (openErrorDialog.value) {
                AlertDialogComponent(
                    onDismissRequest = { },
                    onConfirmation = {
                        openErrorDialog.value = false
                    },
                    dialogTitle = "Error updating event",
                    dialogText = errorMessage.value,
                    onDismissText = "",
                    onConfirmText = "Close"
                )
            }

        }
    }
}