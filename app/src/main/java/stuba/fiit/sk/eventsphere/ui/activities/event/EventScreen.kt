package stuba.fiit.sk.eventsphere.ui.activities.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.model.observeLiveData
import stuba.fiit.sk.eventsphere.model.scheduleNotification
import stuba.fiit.sk.eventsphere.ui.components.CommentBanner
import stuba.fiit.sk.eventsphere.ui.components.FriendBox
import stuba.fiit.sk.eventsphere.ui.components.FriendImageComponent
import stuba.fiit.sk.eventsphere.ui.components.MapLocationPicker
import stuba.fiit.sk.eventsphere.ui.components.SmallButtonComponent
import stuba.fiit.sk.eventsphere.ui.theme.labelStyle
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.EventViewModel
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun EventScreen (
    viewModel: MainViewModel,
    eventViewModel: EventViewModel,
    toBack: () -> Unit,
    toEdit: (id: Int) -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (eventViewModel.event.isInitialized) {
            EventTopBar(
                eventViewModel = eventViewModel,
                toBack = toBack,
                viewModel = viewModel,
                toEdit = toEdit
            )

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                Spacer (
                    modifier = Modifier
                        .height(20.dp)
                )
                OrganizatorViewWidget (
                    eventViewModel = eventViewModel
                )
                Spacer (
                    modifier = Modifier
                        .height(20.dp)
                )
                PerformersViewWidget (
                    eventViewModel = eventViewModel
                )
                Spacer (
                    modifier = Modifier
                        .height(20.dp)
                )

                DescriptionViewWidget (
                    eventViewModel = eventViewModel
                )

                Spacer (
                    modifier = Modifier
                        .height(20.dp)
                )

                CommentsViewWidget (
                    eventViewModel = eventViewModel,
                    mainViewModel = viewModel
                )

                Spacer (
                    modifier = Modifier.height(20.dp)
                )

                MapViewWidget (
                    eventViewModel = eventViewModel
                )
            }
        }
    }
}

@Composable
fun MapViewWidget (
    eventViewModel: EventViewModel
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.where_to_find_us),
            style = welcomeStyle,
            fontSize = 17.sp
        )
        Spacer (
            modifier = Modifier
                .height(10.dp)
        )
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            val properties by remember { mutableStateOf(MapProperties(mapType = MapType.TERRAIN)) }
            val uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = false, compassEnabled = true, mapToolbarEnabled = false, zoomGesturesEnabled = true, scrollGesturesEnabled = true)) }
            val location = LatLng(eventViewModel.event.value?.location?.latitude ?: 0.0, eventViewModel.event.value?.location?.longitude ?: 0.0)

            MapLocationPicker (
                onAdd = {},
                userLocationInput = location ,
                properties = properties,
                uiSettings = uiSettings,
                isForPicking = false
            )
        }
    }
}

@Composable
fun CommentsViewWidget(
    eventViewModel: EventViewModel,
    mainViewModel: MainViewModel
) {
    Column (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Text (
            text = stringResource(id = R.string.comments),
            style = welcomeStyle,
            fontSize = 20.sp,
            modifier = Modifier.padding(15.dp, 0.dp)
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )

        val events = observeLiveData(eventViewModel.event)
        events?.comments?.forEach { comment ->
            CommentBanner(
                id = comment.id,
                firstname = if (mainViewModel.loggedUser.value?.firstname == null) stringResource(id = R.string.firstname) else mainViewModel.loggedUser.value?.firstname ?: "",
                lastname = if (mainViewModel.loggedUser.value?.lastname == null) stringResource(id = R.string.lastname) else mainViewModel.loggedUser.value?.lastname ?: "",
                text = comment.text ?: "",
                image = comment.profile_image,
                onPublish = { },
                isForPublish = false,
            )
            Spacer (modifier = Modifier
                .height(10.dp)
            )
        }
    }
    Spacer (
        modifier = Modifier
            .height(10.dp)
    )
    Column (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Text (
            text = stringResource(id = R.string.publish_your_comment),
            style = welcomeStyle,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(15.dp, 0.dp)
        )
        Spacer (
            modifier = Modifier
                .height(10.dp)
        )

        CommentBanner (
            id = mainViewModel.loggedUser.value?.id ?: 0,
            firstname = if (mainViewModel.loggedUser.value?.firstname == null) stringResource(id = R.string.firstname) else mainViewModel.loggedUser.value?.firstname ?: "",
            lastname = if (mainViewModel.loggedUser.value?.lastname == null) stringResource(id = R.string.lastname) else mainViewModel.loggedUser.value?.lastname ?: "",
            text = stringResource(id = R.string.insert_your_comment),
            image = mainViewModel.loggedUser.value?.profile_image,
            isForPublish = true,
            onPublish = { comment ->
                eventViewModel.viewModelScope.launch {
                    eventViewModel.insertCommentNew(comment, mainViewModel.loggedUser)
                }
            }
        )

    }

}

@Composable
fun DescriptionViewWidget (
    eventViewModel: EventViewModel
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 0.dp)
    ) {
        Text (
            text = stringResource(id = R.string.description),
            style = welcomeStyle,
            fontSize = 18.sp
        )
        Spacer (
            modifier = Modifier
                .height(10.dp)
        )
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(15.dp))
        ) {
            Text(
                text = eventViewModel.event.value?.description ?: stringResource(id = R.string.no_description),
                style = labelStyle,
                fontSize = 17.sp,
                modifier = Modifier.padding(15.dp)
            )
        }
    }
}


@Composable
fun PerformersViewWidget (
    eventViewModel: EventViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.performers),
            style = welcomeStyle,
            fontSize = 20.sp,
            modifier = Modifier.padding(25.dp, 0.dp)
        )
        Spacer(
            modifier = Modifier
                .height(10.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            eventViewModel.event.value?.performers?.forEach { performer ->
                val firstname = if (performer.firstname == null) stringResource(id = R.string.firstname) else performer.firstname ?: ""
                val lastname = if (performer.lastname == null) stringResource(id = R.string.lastname) else performer.lastname ?: ""
                val id = performer.id ?: 0
                FriendBox (
                    firstname = firstname,
                    lastname =lastname,
                    onClick = {},
                    id = id,
                    image = performer.profile_picture
                )
            }

        }
    }
}
@Composable
fun OrganizatorViewWidget (
    eventViewModel: EventViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp)
    ) {
        Box (
            contentAlignment = Alignment.Center
        ) {
            FriendImageComponent (
                image = eventViewModel.event.value?.owner_profile_image
            )
        }
        Spacer(modifier = Modifier
            .width(30.dp))
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.organizator),
                style = welcomeStyle,
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "${eventViewModel.event.value?.owner_firstname.toString()}  ${eventViewModel.event.value?.owner_lastname.toString()}",
                style = welcomeStyle,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun EventTopBar (
    eventViewModel: EventViewModel,
    viewModel: MainViewModel,
    toBack: () -> Unit,
    toEdit: (id: Int) -> Unit
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

                val context = LocalContext.current
                if (eventViewModel.event.value?.owner_id != viewModel.loggedUser.value?.id) {
                    SmallButtonComponent(
                        text = stringResource(id = R.string.notify_me),
                        isSelected = false,
                        onClick = {
                            scheduleNotification(context, 20)
                        }
                    )
                } else {
                    SmallButtonComponent(
                        text = stringResource(id = R.string.edit),
                        isSelected = false,
                        onClick = {
                            toEdit(eventViewModel.event.value?.event_id!!)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = if(eventViewModel.event.value?.title == null) stringResource(id = R.string.tittle)else eventViewModel.event.value?.title ?: "",
                    style = labelStyle,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.background
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = if(eventViewModel.event.value?.location?.address == null) stringResource(id = R.string.somewhere)else eventViewModel.event.value?.location?.address ?: "",
                    style = labelStyle,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.background
                )
                Text(
                    text = if(eventViewModel.event.value?.estimated_end == null) stringResource(id = R.string.over_the_rainbow)else eventViewModel.event.value?.estimated_end ?: "",
                    style = labelStyle,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.background
                )
            }
        }
    }
}