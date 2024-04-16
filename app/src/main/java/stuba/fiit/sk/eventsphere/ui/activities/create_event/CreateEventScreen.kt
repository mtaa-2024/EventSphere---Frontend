package stuba.fiit.sk.eventsphere.ui.activities.create_event

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.ui.components.DateCalendar
import stuba.fiit.sk.eventsphere.ui.components.InputField
import stuba.fiit.sk.eventsphere.ui.theme.labelStyle
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
                        .padding(8.dp)
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