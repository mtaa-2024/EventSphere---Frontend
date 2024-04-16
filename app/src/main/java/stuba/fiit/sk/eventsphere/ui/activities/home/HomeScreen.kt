package stuba.fiit.sk.eventsphere.ui.activities.home

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.model.observeLiveData
import stuba.fiit.sk.eventsphere.ui.components.CategoryBox
import stuba.fiit.sk.eventsphere.ui.components.EventBanner
import stuba.fiit.sk.eventsphere.ui.components.EventSelector
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.HomeViewModel
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel


@Composable
fun HomeScreen (
    profile: () -> Unit,
    back: () -> Unit,
    viewModel: MainViewModel,
    toEvent: (Int) -> Unit,
    homeViewModel: HomeViewModel
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

                Spacer(
                    modifier = Modifier
                        .width(60.dp)
                )

                Text(text = "notifications")

                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                )

                Button(
                    onClick = profile,
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.burger_icon),
                        contentDescription = "Back"
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Let’s explore events you might like",
                style = welcomeStyle,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(
                modifier = Modifier
                    .height(50.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                CategoryBox(
                    icon = R.drawable.book_icon,
                    state = homeViewModel.categories.value?.education ?: false,
                    onClick = homeViewModel::onClickEducation
                )
                CategoryBox(
                    icon = R.drawable.brush_icon,
                    state = homeViewModel.categories.value?.art ?: false,
                    onClick = homeViewModel::onClickArt
                )
                CategoryBox(
                    icon = R.drawable.burger_icon,
                    state = homeViewModel.categories.value?.food ?: false,
                    onClick = homeViewModel::onClickFood
                )
                CategoryBox(
                    icon = R.drawable.music_icon,
                    state = homeViewModel.categories.value?.music ?: false,
                    onClick = homeViewModel::onClickMusic
                )
                CategoryBox(
                    icon = R.drawable.dribbble_icon,
                    state = homeViewModel.categories.value?.sport ?: false,
                    onClick = homeViewModel::onClickSport
                )
            }
            Spacer(
                modifier = Modifier
                    .height(50.dp)
            )
            EventSelector(
                upComingSelected = suspend { homeViewModel.onUpcomingSelect() },
                attendingSelected = suspend { homeViewModel.onAttendingSelect(viewModel) },
                invitedSelected = suspend { homeViewModel.onInvitedSelect(viewModel) },
                buttonState = homeViewModel.eventSelectStates
            )
            Spacer(
                modifier = Modifier
                    .height(40.dp)
            )
        }
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
        ) {
            val eventsState = observeLiveData(homeViewModel.events)

            eventsState?.events?.forEach { event ->
                EventBanner(
                    id = event.id,
                    title = event.title,
                    date = event.date,
                    location = event.location,
                    icon = R.drawable.book_icon,
                    toEvent = toEvent
                )
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )
            }
        }
    }
}