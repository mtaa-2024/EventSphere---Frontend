package stuba.fiit.sk.eventsphere.ui.activities.event

import android.graphics.fonts.Font
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.widget.ContentLoadingProgressBar
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.ui.theme.labelStyle
import stuba.fiit.sk.eventsphere.ui.theme.smallButton
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.EventViewModel
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun EventScreen (
    viewModel: MainViewModel,
    eventViewModel: EventViewModel,
    toBack: () -> Unit
) {
    if (eventViewModel.event.isInitialized) {
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
                        Text(
                            text = eventViewModel.event.value?.title ?: "",
                            style = labelStyle,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.background
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = eventViewModel.event.value?.location ?: "",
                            style = labelStyle,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.background
                        )
                        Text(
                            text = eventViewModel.event.value?.estimated_end ?: "",
                            style = labelStyle,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                }
            }
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
            ) {
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
                            text = eventViewModel.event.value?.owner_firstname.toString() + " " + eventViewModel.event.value?.owner_lastname.toString(),
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
                    Text(text = "Performers", style= labelStyle, fontSize = 24.sp)
                    val performersState = rememberScrollState()
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .horizontalScroll(performersState),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column (
                            modifier = Modifier
                                .fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_circle_24),
                                contentDescription = ""
                            )
                            Text(text = "Linda ritoplesk", style= smallButton, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSecondary)
                        }
                        Spacer(modifier = Modifier.width(10.dp))

                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Description", style= labelStyle, fontSize = 24.sp)
                }
                //Performers

                //Description

                //Comments

                //Map
            }
        }
    } else {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = ""
        )
    }
}