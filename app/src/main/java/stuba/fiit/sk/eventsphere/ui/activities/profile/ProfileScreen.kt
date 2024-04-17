package stuba.fiit.sk.eventsphere.ui.activities.profile

import androidx.compose.foundation.Image
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
import androidx.compose.material3.TextButton
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
import stuba.fiit.sk.eventsphere.ui.components.PrimaryButton
import stuba.fiit.sk.eventsphere.ui.theme.buttonStyle
import stuba.fiit.sk.eventsphere.ui.theme.labelStyle
import stuba.fiit.sk.eventsphere.ui.theme.smallButton
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel
import stuba.fiit.sk.eventsphere.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen (
    home: () -> Unit,
    toWelcomeScreen: () -> Unit,
    toEventCenter: () -> Unit,
    toEditProfile: () -> Unit,
    toFriends: () -> Unit,
    viewModel: MainViewModel,
    profileViewModel: ProfileViewModel
) {
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
                    onClick = home,
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.back_arrow),
                        contentDescription = "Back"
                    )
                }
                TextButton(
                    onClick = toEditProfile
                ) {
                    Text(
                        text = "Edit Profile",
                        style = labelStyle,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

        val profileScroll = rememberScrollState()

        Column (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(profileScroll),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )
            Text(text = "profile_img")

            Spacer(
                modifier = Modifier
                    .height(15.dp)
            )
            val firstName = viewModel.loggedUser.value?.firstname ?: "Firstname"
            val lastName = viewModel.loggedUser.value?.lastname ?: "Lastname"

            Text(
                text = "$firstName $lastName",
                style = welcomeStyle,
                fontSize = 20.sp,
            )

            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Friends",
                    style = welcomeStyle,
                    fontSize = 20.sp
                )

                TextButton(
                    onClick = toEditProfile
                ) {
                    Text(
                        text = "Search user",
                        style = buttonStyle,
                        fontSize = 20.sp
                    )
                }
            }
            val friendsState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(10.dp)
                        .horizontalScroll(friendsState),
                    horizontalArrangement = Arrangement.Start
                ) {
                    profileViewModel.friends.value?.listFriends?.forEach() { friend ->
                        Column(
                            modifier = Modifier
                                .fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_circle_24),
                                contentDescription = ""
                            )
                            Text(
                                text = friend.firstname.toString() + " " + friend.lastname.toString(),
                                style = smallButton,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        PrimaryButton(text = "Event Center", onClick = toEventCenter)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Spacer(
                            modifier = Modifier
                                .width(30.dp)
                        )

                        Text(
                            text = "Settings",
                            style = welcomeStyle,
                            fontSize = 20.sp
                        )

                        Spacer(
                            modifier = Modifier
                                .width(5.dp)
                        )

                        Image(
                            painter = painterResource(id = R.drawable.setting_icon),
                            contentDescription = "Settings",
                            contentScale = ContentScale.FillBounds,
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                    )

                    Column(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = "Notifications",
                                style = welcomeStyle,
                                fontSize = 20.sp
                            )

                            Text(
                                text = "Notifications",
                                style = welcomeStyle,
                                fontSize = 20.sp
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = "Color mode",
                                style = welcomeStyle,
                                fontSize = 20.sp
                            )

                            Text(
                                text = "Color mode",
                                style = welcomeStyle,
                                fontSize = 20.sp
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = "Language",
                                style = welcomeStyle,
                                fontSize = 20.sp
                            )

                            Text(
                                text = "Language",
                                style = welcomeStyle,
                                fontSize = 20.sp
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )

                TextButton(
                    onClick = toWelcomeScreen
                ) {
                    Text(
                        text = "Logout",
                        style = labelStyle,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}