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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.ui.components.CategoryBox
import stuba.fiit.sk.eventsphere.ui.components.EventBanner
import stuba.fiit.sk.eventsphere.ui.components.HomeSelectorSelected
import stuba.fiit.sk.eventsphere.ui.components.HomeSelectorUnselected
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun HomeScreen (
    profile: () -> Unit,
    back: () -> Unit,
    viewModel: MainViewModel,
) {
    val homeViewModel = HomeViewModel()
    Column (
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
                Text(text = "Profile")
                Text(text = "notifications")
            }
        }
        Column (
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
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                CategoryBox (
                    icon = R.drawable.book_icon,
                    value = homeViewModel.getEducationState(),
                    onClick = homeViewModel::onClickEducation
                )
                CategoryBox (
                    icon = R.drawable.brush_icon,
                    value = homeViewModel.getArtState(),
                    onClick = homeViewModel::onClickArt
                )
                CategoryBox (
                    icon = R.drawable.burger_icon,
                    value = homeViewModel.getFoodState(),
                    onClick = homeViewModel::onClickFood
                )
                CategoryBox (
                    icon = R.drawable.music_icon,
                    value = homeViewModel.getMusicState(),
                    onClick = homeViewModel::onClickMusic
                )
                CategoryBox (
                    icon = R.drawable.dribbble_icon,
                    value = homeViewModel.getSportState(),
                    onClick = homeViewModel::onClickSport
                )
            }
            Spacer (
                modifier = Modifier
                    .height(50.dp)
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                HomeSelectorSelected("Upcoming")
                HomeSelectorUnselected("Attending")
                HomeSelectorUnselected("Invited")
            }
            Spacer (
                modifier = Modifier
                    .height(40.dp)
            )
        }
        val scrollState = rememberScrollState()
        Column (
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
        ) {
            viewModel.upcoming.value?.events?.forEach { event ->
                EventBanner (
                    title = event.title,
                    date = event.date,
                    location = event.location,
                    icon = R.drawable.book_icon
                )
                Spacer (
                    modifier = Modifier
                        .height(10.dp)
                )
            }
        }
    }
}