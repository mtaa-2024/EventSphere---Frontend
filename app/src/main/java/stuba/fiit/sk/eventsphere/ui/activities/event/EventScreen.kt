package stuba.fiit.sk.eventsphere.ui.activities.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.model.CommentsView
import stuba.fiit.sk.eventsphere.model.observeLiveData
import stuba.fiit.sk.eventsphere.ui.components.CommentBanner
import stuba.fiit.sk.eventsphere.ui.components.CommentEditBanner
import stuba.fiit.sk.eventsphere.ui.components.CommentInputBanner
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
                        eventViewModel.event.value?.performers?.forEach { performer ->
                            Column (
                                modifier = Modifier
                                    .fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.baseline_circle_24),
                                    contentDescription = ""
                                )
                                Text(text = performer.firstname.toString() + " " + performer.lastname.toString(), style= smallButton, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSecondary)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                        }

                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    val descriptionState = rememberScrollState()

                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 15.dp,
                                    topEnd = 15.dp,
                                    bottomStart = 15.dp,
                                    bottomEnd = 15.dp
                                )
                            )
                            .border(
                                1.dp,
                                Color(
                                    red = 0.917129635810852f,
                                    green = 0.917129635810852f,
                                    blue = 0.917129635810852f,
                                    alpha = 1f
                                ),
                                RoundedCornerShape(
                                    topStart = 15.dp,
                                    topEnd = 15.dp,
                                    bottomStart = 15.dp,
                                    bottomEnd = 15.dp
                                )
                            )
                            .background(MaterialTheme.colorScheme.background)
                            .padding(10.dp)
                            .verticalScroll(descriptionState)
                    ) {
                        Text(text = "Description", style= labelStyle, fontSize = 24.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = eventViewModel.event.value?.description ?: "No desciption provided")
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Comments", style= labelStyle, fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(20.dp))

                    val commentState = observeLiveData(eventViewModel.event)

                    Column (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        commentState?.comments?.forEach { comment ->
                            if (comment.id != viewModel.loggedUser.value?.id) {
                                CommentBanner(
                                    image = null,
                                    firstname = comment.firstname,
                                    lastname = comment.lastname,
                                    text = comment.text
                                )
                                Spacer(modifier = Modifier.height(30.dp))
                            }
                        }
                    }
                    
                    Text(text = "Your comment", style= labelStyle, fontSize = 17.sp)

                    Spacer(modifier = Modifier.height(15.dp))


                    var isComment by remember { mutableStateOf(false) }

                    commentState?.comments?.forEach { comment ->
                        if (comment.id == viewModel.loggedUser.value?.id) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.End
                            ) {
                                CommentEditBanner (
                                    image = null,
                                    firstname = comment?.firstname,
                                    lastname = comment?.lastname,
                                    text = comment?.text
                                )
                                Spacer(modifier = Modifier.height(30.dp))
                            }
                            isComment = true
                        }
                    }

                    if (!isComment) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            CommentInputBanner(
                                image = viewModel.loggedUser.value?.profile_image,
                                firstname = viewModel.loggedUser.value?.firstname,
                                lastname = viewModel.loggedUser.value?.lastname,
                                text = "Insert your comment",
                                onChange = eventViewModel::updateComment,
                                onPublish = {
                                    eventViewModel.viewModelScope.launch {
                                        eventViewModel.publishComment(viewModel.loggedUser.value?.id)
                                    }
                                }
                            )
                        }
                    }

                    Text(text="Where to find us", style = labelStyle, fontSize = 24.sp)


                }
            }
        }
    } else {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = ""
        )
    }
}