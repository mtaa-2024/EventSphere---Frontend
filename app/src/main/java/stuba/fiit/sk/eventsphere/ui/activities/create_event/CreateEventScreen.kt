package stuba.fiit.sk.eventsphere.ui.activities.create_event

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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.model.FriendsView
import stuba.fiit.sk.eventsphere.model.observeLiveData
import stuba.fiit.sk.eventsphere.ui.components.DateCalendar
import stuba.fiit.sk.eventsphere.ui.components.HomeSelectorSelected
import stuba.fiit.sk.eventsphere.ui.components.HomeSelectorUnselected
import stuba.fiit.sk.eventsphere.ui.components.InputField
import stuba.fiit.sk.eventsphere.ui.components.InputFieldCreateEvent
import stuba.fiit.sk.eventsphere.ui.components.PrimaryButton
import stuba.fiit.sk.eventsphere.ui.theme.labelStyle
import stuba.fiit.sk.eventsphere.ui.theme.smallButton
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.CreateEventViewModel
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun CreateEventScreen(
    toBack: () -> Unit,
    viewModel: MainViewModel,
    createEventViewModel: CreateEventViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.event_banner),
                contentDescription = "welcome_background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Column(
                modifier = Modifier.matchParentSize()
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
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
                    Box() {}
                    HomeSelectorUnselected(value = "Save", onSelect = {  }, onClick = { createEventViewModel.viewModelScope.launch {
                        val id = createEventViewModel.createEvent()
                    }
                    toBack()
                    } )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InputFieldCreateEvent(
                        label = "Title",
                        value = createEventViewModel.eventData.value?.title.toString(),
                        onChange = createEventViewModel::updateTitle
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        DateCalendar(
                            dateStructure = createEventViewModel.date,
                            updateEstimatedEnd = createEventViewModel::updateEstimatedEnd
                        )
                        //
                        Image(
                            painter = painterResource(id = R.drawable.location),
                            contentDescription = "",
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.background))
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_circle_24),
                contentDescription = "s"
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Organizator",
                    style = welcomeStyle,
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = viewModel.loggedUser.value?.firstname.toString() + " " + viewModel.loggedUser.value?.lastname.toString(),
                    style = welcomeStyle,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Performers", style = labelStyle, fontSize = 24.sp)
            val performersState = rememberScrollState()
            var addingPerformer by remember { mutableStateOf(false) }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .horizontalScroll(performersState),
                horizontalArrangement = Arrangement.Center
            ) {
                Box ( 
                    modifier = Modifier
                        .clickable(onClick = { addingPerformer = true } ),
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_circle_24),
                            contentDescription = ""
                        )
                        Text(text = "Add performer")
                    }
                }
            }

            if (addingPerformer) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    var isSelectedByFriends by remember { mutableStateOf(createEventViewModel.addPerformerState.value?.friend ?: false) }
                    var isSelectedByInput by remember { mutableStateOf(createEventViewModel.addPerformerState.value?.input ?: false) }
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {
                        if (isSelectedByFriends) HomeSelectorSelected(
                            value = "Select friend"
                        ) else HomeSelectorUnselected(
                            value = "Select friend",
                            onSelect = {
                                isSelectedByFriends = !isSelectedByFriends
                                isSelectedByInput = false
                            },
                            onClick = { createEventViewModel.onFriendSelect() }
                        )
                        if (isSelectedByInput) HomeSelectorSelected(
                            value = "Input name"
                        ) else HomeSelectorUnselected(
                            value = "Input name",
                            onSelect = {
                                isSelectedByFriends = false
                                isSelectedByInput = !isSelectedByInput
                            },
                            onClick = { createEventViewModel.onInputSelect() }
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                    if (isSelectedByFriends) {
                        val rowState = rememberScrollState()
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .padding(10.dp)
                                .horizontalScroll(rowState),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            createEventViewModel.friends.value?.listFriends?.forEach { friend ->
                                if (!createEventViewModel.performerList.contains(friend)) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Box(
                                            modifier = Modifier.clickable(onClick = {
                                                createEventViewModel.addPerformer(
                                                    friend
                                                )
                                            })
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxHeight(),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.baseline_circle_24),
                                                    contentDescription = ""
                                                )
                                                Text(
                                                    text = friend.firstname.toString() + " " + friend.lastname.toString(),
                                                    style = smallButton,
                                                    fontSize = 15.sp,
                                                    color = MaterialTheme.colorScheme.onSecondary
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (isSelectedByInput) {
                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            val (firstname, setFirstname) = remember { mutableStateOf("Firstname") }
                            val (lastname, setLastname) = remember { mutableStateOf("Lastname") }

                            InputField(label = "Firstname", value = firstname, onChange = { newValue ->
                                setFirstname(newValue) } )
                            Spacer(modifier = Modifier.height(20.dp))
                            InputField(label = "Lastname", value = lastname, onChange = { newValue ->
                                setLastname(newValue)} )

                            PrimaryButton(text = "Add", onClick = {
                                createEventViewModel.addPerformer(FriendsView(id = null, firstname = firstname, lastname = lastname, profile_picture = null))
                                createEventViewModel.onFriendSelect()
                                isSelectedByFriends = true
                                isSelectedByInput = false
                            })
                        }
                    }
                }

            }

        }
        val descriptionState = rememberScrollState()
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 15.dp,
                        topEnd = 15.dp,
                        bottomStart = 15.dp,
                        bottomEnd = 15.dp
                    )
                )
                .border(
                    1.dp,
                    Color(
                        red = 0.917129635810852f,
                        green = 0.917129635810852f,
                        blue = 0.917129635810852f,
                        alpha = 1f
                    ),
                    RoundedCornerShape(
                        topStart = 15.dp,
                        topEnd = 15.dp,
                        bottomStart = 15.dp,
                        bottomEnd = 15.dp
                    )
                )
                .background(MaterialTheme.colorScheme.background)
                .padding(10.dp)
                .verticalScroll(descriptionState)
        ) {
            Text(text = "Description", style= labelStyle, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(10.dp))
            InputField(label = "Description", value = createEventViewModel.eventData.value?.description ?: "Description", onChange = createEventViewModel::updateDescription)
        }
    }
}