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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.ui.components.DateCalendar
import stuba.fiit.sk.eventsphere.ui.components.HomeSelectorUnselected
import stuba.fiit.sk.eventsphere.ui.components.InputField
import stuba.fiit.sk.eventsphere.ui.components.InputFieldCreateEvent
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
                    HomeSelectorUnselected(value = "Save", onSelect = {  }, onClick = {} )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

/*
                    InputField(
                        label = "Title",
                        value = createEventViewModel.eventData.value?.title.toString(),
                        onChange = createEventViewModel::updateTitle
                    )

                    InputField(
                        label = "Location",
                        value = createEventViewModel.eventData.value?.location.toString(),
                        onChange = createEventViewModel::updateLocation
                    )

*/


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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .horizontalScroll(performersState),
                horizontalArrangement = Arrangement.Center
            ) {
                Box ( 
                    modifier = Modifier.clickable(onClick = {}),
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
        }
    }
}