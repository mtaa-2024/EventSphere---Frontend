package stuba.fiit.sk.eventsphere.ui.activities.home

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.model.observeLiveData
import stuba.fiit.sk.eventsphere.ui.components.AlertDialogComponent
import stuba.fiit.sk.eventsphere.ui.components.CategoryBox
import stuba.fiit.sk.eventsphere.ui.components.EventBanner
import stuba.fiit.sk.eventsphere.ui.components.FriendImageComponent
import stuba.fiit.sk.eventsphere.ui.components.SearchBarComponent
import stuba.fiit.sk.eventsphere.ui.components.SmallButtonComponent
import stuba.fiit.sk.eventsphere.ui.components.TopBarProfileComponent
import stuba.fiit.sk.eventsphere.ui.theme.LightColorScheme
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.HomeViewModel
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel


@Preview(showBackground = true)
@Composable
fun Preview () {
    HomeScreen(toProfile = {}, toBack = {}, viewModel = MainViewModel(), homeViewModel = HomeViewModel(MainViewModel()), toEvent = {})
}

@Composable
fun HomeScreen (
    toProfile: () -> Unit,
    viewModel: MainViewModel,
    toEvent: (Int) -> Unit,
    toBack:() -> Unit,
    homeViewModel: HomeViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeTopBar(
            viewModel = viewModel,
            toProfile = toProfile,
            toBack = toBack
        )

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(
                modifier = Modifier
                    .height(25.dp)
            )
            Text(
                text = "Let’s explore events you might like",
                style = welcomeStyle,
                fontSize = 22.sp
            )

            Spacer(
                modifier = Modifier
                    .height(25.dp)
            )

            SearchBarComponent(
                onUpdate = {},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier
                    .height(25.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                CategoryBox(
                    icon = R.drawable.book_icon,
                    initializeState = homeViewModel.categories.value?.education ?: false,
                    onClick = homeViewModel::onClickEducation
                )
                CategoryBox(
                    icon = R.drawable.music_icon,
                    initializeState = homeViewModel.categories.value?.music ?: false,
                    onClick = homeViewModel::onClickMusic
                )
                CategoryBox(
                    icon = R.drawable.burger_icon,
                    initializeState = homeViewModel.categories.value?.food ?: false,
                    onClick = homeViewModel::onClickFood
                )
                CategoryBox(
                    icon = R.drawable.brush_icon,
                    initializeState = homeViewModel.categories.value?.art ?: false,
                    onClick = homeViewModel::onClickArt
                )
                CategoryBox(
                    icon = R.drawable.dribbble_icon,
                    initializeState = homeViewModel.categories.value?.sport ?: false,
                    onClick = homeViewModel::onClickSport
                )
            }

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            EventViewButtons(
                homeViewModel = homeViewModel
            )

            Spacer(
                modifier = Modifier
                    .height(50.dp)
            )
        }

        val scrollState = rememberScrollState()
        Column (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            val eventsState = observeLiveData(homeViewModel.events)
            eventsState?.events?.forEach { event ->
                EventBanner(
                    id = event.id,
                    title = event.title ?: "",
                    date = event.date ?: "",
                    location = event.location ?: "",
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

@Composable
fun HomeTopBar(
    viewModel: MainViewModel,
    toProfile: () -> Unit,
    toBack: () -> Unit
) {
    var openAlertDialog by remember { mutableStateOf(false) }

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
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box() {}
            Box(
                modifier = Modifier
                    .clickable(onClick = {
                        if(viewModel.loggedUser.value?.id != null) toProfile() else openAlertDialog = true
                    }
                )
            ) {
                TopBarProfileComponent(
                    image = viewModel.loggedUser.value?.profile_image,
                )
            }
            Box {
                Image(
                    painter = painterResource(id = R.drawable.notification),
                    contentDescription = "notification"
                )
            }
        }
    }
    if (openAlertDialog) {
        AlertDialogComponent(
            onDismissRequest = { openAlertDialog = false },
            onConfirmation = {
                openAlertDialog = false
                toBack() },
            onConfirmText = "Login or register",
            onDismissText = "Close",
            dialogText = "You have to be logged to access profile, you can continue as guest or login to your existing account or create new account",
            dialogTitle = "Guest"
        )
    }
}

@Composable
fun EventViewButtons (
    homeViewModel: HomeViewModel
) {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        var isSelectedUpcoming by remember { mutableStateOf(homeViewModel.eventSelectStates.value?.upcoming?: false) }
        var isSelectedAttending by remember { mutableStateOf(homeViewModel.eventSelectStates.value?.attending?: false) }
        var isSelectedInvited by remember { mutableStateOf(homeViewModel.eventSelectStates.value?.invited?: false) }

        SmallButtonComponent (
            text = "Upcoming",
            isSelected = isSelectedUpcoming,
            onClick = {
                homeViewModel.onUpcomingSelect()
                isSelectedUpcoming = true
                isSelectedAttending = false
                isSelectedInvited = false
            }
        )

        SmallButtonComponent (
            text = "Attending",
            isSelected = isSelectedAttending,
            onClick = {
                homeViewModel.onAttendingSelect()
                isSelectedUpcoming = false
                isSelectedAttending = true
                isSelectedInvited = false
            }
        )

        SmallButtonComponent (
            text = "Invited",
            isSelected = isSelectedInvited,
            onClick = {
                homeViewModel.onInvitedSelect()
                isSelectedUpcoming = false
                isSelectedAttending = false
                isSelectedInvited = true
            }
        )

    }

}