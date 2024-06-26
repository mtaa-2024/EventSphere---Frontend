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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.model.Event
import stuba.fiit.sk.eventsphere.model.observeLiveData
import stuba.fiit.sk.eventsphere.ui.components.AlertDialogComponent
import stuba.fiit.sk.eventsphere.ui.components.CategoryBox
import stuba.fiit.sk.eventsphere.ui.components.EventBanner
import stuba.fiit.sk.eventsphere.ui.components.SearchBarComponent
import stuba.fiit.sk.eventsphere.ui.components.SmallButtonComponent
import stuba.fiit.sk.eventsphere.ui.components.TopBarProfileComponent
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.HomeViewModel
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel


@Composable
fun HomeScreen (
    toProfile: () -> Unit,
    toGroupChat: () -> Unit,
    viewModel: MainViewModel,
    toEvent: (Event) -> Unit,
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
            toBack = toBack,
            toChat = toGroupChat
        )

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(
                modifier = Modifier
                    .height(25.dp)
            )
            Text(
                text = stringResource(id = R.string.home_welcome_text),
                style = welcomeStyle,
                fontSize = 22.sp
            )

            Spacer(
                modifier = Modifier
                    .height(25.dp)
            )

            SearchBarComponent(
                onUpdate = {
                    homeViewModel.viewModelScope.launch {
                        homeViewModel.onUpdateFilter(it)
                    }
                },
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
            if (eventsState?.events?.isEmpty() == true || eventsState?.events == null) {
                Text (
                    text = stringResource(id = R.string.no_events_found),
                    style = welcomeStyle,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else {
                eventsState.events?.forEach { event ->
                    EventBanner(
                        event = event,
                        title = event.title,
                        date = event.estimatedEnd,
                        location = event.location,
                        icon = if (event.category == 1) R.drawable.book_icon else if (event.category == 2) R.drawable.music_icon else if (event.category == 3) R.drawable.burger_icon else if (event.category == 4) R.drawable.brush_icon else R.drawable.dribbble_icon,
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
}

@Composable
fun HomeTopBar(
    viewModel: MainViewModel,
    toProfile: () -> Unit,
    toBack: () -> Unit,
    toChat: () -> Unit
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
                .padding(25.dp, 0.dp)
                .matchParentSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box (
                modifier = Modifier
                    .clickable { toChat() },
            ) {
                Image(
                    modifier = Modifier.size(35.dp),
                    painter = painterResource(id = R.drawable.chat_icon),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background),
                    contentDescription = "Back"
                )
            }

            Box(
                modifier = Modifier
                    .clickable(onClick = {
                        if(viewModel.loggedUser.value?.id != null) toProfile() else openAlertDialog = true
                    }
                )
            ) {
                TopBarProfileComponent(
                    image = viewModel.loggedUser.value?.profileImage,
                )
            }

            Box {
                Image(
                    painter = painterResource(id = R.drawable.notification),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background),
                    contentDescription = stringResource(id = R.string.notification)
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
            onConfirmText = stringResource(id = R.string.login_or_register),
            onDismissText = stringResource(id = R.string.close),
            dialogText = stringResource(id = R.string.alert_dialog_text),
            dialogTitle = stringResource(id = R.string.alert_dialog_title)
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
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        var isSelectedUpcoming by remember { mutableStateOf(homeViewModel.eventSelectStates.value?.upcoming?: false) }
        var isSelectedAttending by remember { mutableStateOf(homeViewModel.eventSelectStates.value?.attending?: false) }
        var isSelectedInvited by remember { mutableStateOf(homeViewModel.eventSelectStates.value?.invited?: false) }

        SmallButtonComponent (
            text = stringResource(id = R.string.upcoming),
            isSelected = isSelectedUpcoming,
            onClick = {
                homeViewModel.onUpcomingSelect()
                isSelectedUpcoming = true
                isSelectedAttending = false
                isSelectedInvited = false
            }
        )

        SmallButtonComponent (
            text = stringResource(id = R.string.attending),
            isSelected = isSelectedAttending,
            onClick = {
                homeViewModel.onAttendingSelect()
                isSelectedUpcoming = false
                isSelectedAttending = true
                isSelectedInvited = false
            }
        )

        SmallButtonComponent (
            text = stringResource(id = R.string.invited),
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