package stuba.fiit.sk.eventsphere.ui.activities.event_center

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.model.observeLiveData
import stuba.fiit.sk.eventsphere.ui.components.ButtonComponent
import stuba.fiit.sk.eventsphere.ui.components.EventBanner
import stuba.fiit.sk.eventsphere.ui.components.SmallButtonComponent
import stuba.fiit.sk.eventsphere.ui.theme.LightColorScheme
import stuba.fiit.sk.eventsphere.viewmodel.EventCenterViewModel
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Preview(showBackground = true)
@Composable
fun Preview () {
    EventCenterScreen(viewModel = MainViewModel(), eventCenterViewModel = EventCenterViewModel(
        MainViewModel()
    ), back = {}, toEvent = {}, toCreateEvent = {})
}

@Composable
fun EventCenterScreen (
    viewModel: MainViewModel,
    eventCenterViewModel: EventCenterViewModel,
    back: () -> Unit,
    toEvent: (Int) -> Unit,
    toCreateEvent: () -> Unit
) {

    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EventCenterTopBar (
            back = back
        )

        Spacer (
            modifier = Modifier
                .height(30.dp)
        )

        var isSelectedUpcoming by remember { mutableStateOf(eventCenterViewModel.eventSelectStates.value?.upcoming) }
        var isSelectedExpired by remember { mutableStateOf(eventCenterViewModel.eventSelectStates.value?.expired) }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 0.dp)
        ) {
            ButtonComponent(
                onClick = { toCreateEvent() },
                fillColor = LightColorScheme.primary,
                textColor = LightColorScheme.background,
                text = stringResource(id = R.string.create_your_event_text),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer (
                modifier = Modifier
                    .height(50.dp)
            )

            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                SmallButtonComponent (
                    text = stringResource(id = R.string.upcoming),
                    isSelected = eventCenterViewModel.eventSelectStates.value?.upcoming ?: false,
                    onClick = {
                        eventCenterViewModel.onUpcomingSelect(viewModel)
                        isSelectedUpcoming = true
                        isSelectedExpired = false
                    }
                )
                SmallButtonComponent (
                    text = stringResource(id = R.string.expired),
                    isSelected = eventCenterViewModel.eventSelectStates.value?.expired ?: false,
                    onClick = {
                        eventCenterViewModel.onExpiredSelect(viewModel)
                        isSelectedUpcoming = false
                        isSelectedExpired = true
                    }
                )
            }

            Spacer (
                modifier = Modifier
                    .height(80.dp)
            )
        }
        var eventsState = observeLiveData(eventCenterViewModel.upcoming)
        if (isSelectedUpcoming == true) {
            eventsState = observeLiveData(eventCenterViewModel.upcoming)
        } else if (isSelectedExpired == true) {
            eventsState = observeLiveData(eventCenterViewModel.expired)
        }
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
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

    /*

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            PrimaryButton(text = "Create your event", onClick = toCreateEvent )
            Spacer(modifier = Modifier.height(50.dp))
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                var isSelectedUpComing by remember { mutableStateOf(eventCenterViewModel.eventSelectStates.value?.upcoming ?: false) }
                var isSelectedExpired by remember { mutableStateOf(eventCenterViewModel.eventSelectStates.value?.expired ?: false) }

                if (isSelectedUpComing) HomeSelectorSelected(
                    value = "Upcoming"
                ) else HomeSelectorUnselected(
                    value = "Upcoming",
                    onSelect = {
                        isSelectedUpComing = !isSelectedUpComing
                        isSelectedExpired = false
                    },
                    onClick = { eventCenterViewModel.onUpcomingSelect(viewModel) }
                )
                if (isSelectedExpired) HomeSelectorSelected(
                    value = "Expired"
                ) else HomeSelectorUnselected(
                    value = "Expired",
                    onSelect = {
                        isSelectedUpComing = false
                        isSelectedExpired = !isSelectedExpired
                    },
                    onClick = { eventCenterViewModel.onExpiredSelect(viewModel) }
                )
            }
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                if (eventCenterViewModel.eventSelectStates.value?.upcoming == true) {
                    val eventsState = observeLiveData(eventCenterViewModel.upcoming)

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
                } else if (eventCenterViewModel.eventSelectStates.value?.expired == true) {
                    val eventsState = observeLiveData(eventCenterViewModel.expired)

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
    }

     */
}

@Composable
fun EventCenterTopBar (
    back: () -> Unit,
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
                .padding(20.dp)
                .matchParentSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .clickable(onClick = back)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "Back"
                )
            }

        }
    }
}