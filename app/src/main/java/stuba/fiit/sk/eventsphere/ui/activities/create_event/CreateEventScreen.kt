package stuba.fiit.sk.eventsphere.ui.activities.create_event

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.ui.components.DateCalendar
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
                    Text(text = "Profile")
                    Text(text = "notifications")
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


                    Box(
                        modifier = Modifier
                            .width(200.dp)
                            .height(48.dp)
                            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp, bottomStart = 15.dp, bottomEnd = 15.dp))
                            .border(1.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp, bottomStart = 15.dp, bottomEnd = 15.dp))
                            .background(MaterialTheme.colorScheme.background)
                    ) {

                    }

                    DateCalendar(
                        dateStructure = createEventViewModel.date,
                        updateEstimatedEnd = createEventViewModel::updateEstimatedEnd
                    )
                }
            }
        }
        Column (

        ) {

        }
    }
}