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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.model.observeLiveData
import stuba.fiit.sk.eventsphere.ui.components.EventBanner
import stuba.fiit.sk.eventsphere.ui.components.HomeSelectorSelected
import stuba.fiit.sk.eventsphere.ui.components.HomeSelectorUnselected
import stuba.fiit.sk.eventsphere.ui.components.PrimaryButton
import stuba.fiit.sk.eventsphere.viewmodel.EventCenterViewModel
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun EventCenterScreen (
    viewModel: MainViewModel,
    eventCenterViewModel: EventCenterViewModel,
    toBack: () -> Unit,
    toEvent: (id: Int) -> Unit
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
                    .padding(20.dp)
                    .matchParentSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .clickable(onClick = toBack)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.back_arrow),
                        contentDescription = "Back"
                    )
                }

            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            PrimaryButton(text = "Create your event", onClick = {} )
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
}