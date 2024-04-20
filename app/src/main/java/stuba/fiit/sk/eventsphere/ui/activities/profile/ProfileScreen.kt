package stuba.fiit.sk.eventsphere.ui.activities.profile


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.ui.components.ButtonComponent
import stuba.fiit.sk.eventsphere.ui.components.FriendImageComponent
import stuba.fiit.sk.eventsphere.ui.components.ProfileImageComponent
import stuba.fiit.sk.eventsphere.ui.components.SmallButtonComponent
import stuba.fiit.sk.eventsphere.ui.theme.LightColorScheme
import stuba.fiit.sk.eventsphere.ui.theme.labelStyle
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel
import stuba.fiit.sk.eventsphere.viewmodel.ProfileViewModel

@Preview(showBackground = true)
@Composable
fun Preview () {
    ProfileScreen(toLogout = {}, back = {}, toEditProfile = {}, toEventCenter = {}, toFriend = {}, toSearchUser = {}, viewModel = MainViewModel(), profileViewModel = ProfileViewModel(1))
}


@Composable
fun ProfileScreen (
    toLogout: () -> Unit,
    back: () -> Unit,
    toEventCenter: () -> Unit,
    toEditProfile: () -> Unit,
    toFriend: (id:Int?) -> Unit,
    toSearchUser: ()-> Unit,
    viewModel: MainViewModel,
    profileViewModel: ProfileViewModel
) {

    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileTopBar (
            back = back,
            toEditProfile = toEditProfile
        )

        val scrollState = rememberScrollState()

        Column (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer (
                modifier = Modifier
                    .height(10.dp)
            )

            ProfileImageComponent (
                image = viewModel.loggedUser.value?.profile_image
            )

            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )

            val firstName = viewModel.loggedUser.value?.firstname ?: "Firstname"
            val lastName = viewModel.loggedUser.value?.lastname ?: "Lastname"

            Text(
                text = "$firstName $lastName",
                style = welcomeStyle,
                color = LightColorScheme.onBackground,
                fontSize = 20.sp,
            )

            Spacer (
                modifier = Modifier
                    .height(20.dp)
            )

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text (
                    text = "Friends",
                    style = welcomeStyle,
                    fontSize = 20.sp
                )
                //idk why to nefunguje ale opravis<3
                 /*
                SmallButtonComponent (
                    text = "Search friend",
                    isSelected = true,
                    onClick = { toSearchUser() }
                )*/
                Button(
                    onClick = { toSearchUser() }) {

                    Text(text = "search" )                 }
            }

            val friendScrollState = rememberScrollState()

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .horizontalScroll(friendScrollState),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                profileViewModel.friends.value?.listFriends?.forEach { friend ->
                    if (friend.firstname != null && friend.lastname != null) {
                        FriendBox(
                            firstname = friend.firstname!!,
                            lastname = friend.lastname!!,
                            onClick = toFriend,
                            id = friend.id!!,
                            image = friend.profile_picture
                        )
                    }
                }
            }

            Spacer (
                modifier = Modifier
                    .height(20.dp)
            )

            Column (
                modifier = Modifier
                    .padding(25.dp, 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ButtonComponent (
                    onClick = { toEventCenter() },
                    fillColor = LightColorScheme.primary,
                    textColor = LightColorScheme.background,
                    text = "Event center",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                )

                //Settings
                Spacer (
                    modifier = Modifier
                        .height(30.dp)
                )

                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .background(LightColorScheme.background)
                        .border(
                            BorderStroke(1.dp, LightColorScheme.primary),
                            shape = RoundedCornerShape(15.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column (
                        modifier = Modifier
                            .padding(15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text (
                            text = "Settings",
                            style = labelStyle,
                            fontSize = 24.sp
                        )

                        Spacer (
                            modifier = Modifier
                                .height(10.dp)
                        )


                        Row (
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {

                            Column(
                                modifier = Modifier,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Notifications",
                                    style = labelStyle,
                                    fontSize = 18.sp
                                )
                                Spacer (
                                    modifier = Modifier
                                        .height(10.dp)
                                )
                                Text(
                                    text = "Dark mode",
                                    style = labelStyle,
                                    fontSize = 18.sp
                                )
                                Spacer (
                                    modifier = Modifier
                                        .height(10.dp)
                                )
                                Text(
                                    text = "Language",
                                    style = labelStyle,
                                    fontSize = 18.sp,
                                )

                            }

                            Column(
                                modifier = Modifier,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Set",
                                    style = labelStyle,
                                    fontSize = 18.sp
                                )
                                Spacer (
                                    modifier = Modifier
                                        .height(10.dp)
                                )
                                Text(
                                    text = "Set",
                                    style = labelStyle,
                                    fontSize = 18.sp
                                )
                                Spacer (
                                    modifier = Modifier
                                        .height(10.dp)
                                )
                                Text(
                                    text = "Set",
                                    style = labelStyle,
                                    fontSize = 18.sp
                                )

                            }
                        }
                    }
                }
            }
            Spacer (
                modifier = Modifier
                    .weight(1f)
            )

            SmallButtonComponent (
                text = "Logout",
                isSelected = false,
                onClick = toLogout,
            )

            Spacer (
                modifier = Modifier
                    .height(50.dp)
            )
        }
    }
}

@Composable
fun ProfileTopBar (
    back: () -> Unit,
    toEditProfile: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.top_bar),
            contentDescription = "welcome_background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
        )
        Row (
            modifier = Modifier
                .padding(10.dp)
                .matchParentSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button (
                onClick = back,
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "Back"
                )
            }
            SmallButtonComponent (
                text = "Edit profile",
                isSelected = false,
                onClick = { toEditProfile() }
            )
        }
    }
}

@Composable
fun FriendBox (
    firstname: String,
    lastname: String,
    image: ImageBitmap?,
    onClick: (id: Int?) -> Unit,
    id: Int
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box (
            modifier = Modifier
                .clickable(
                    onClick = { onClick(id) }
                ),
            contentAlignment = Alignment.Center
        ) {
            FriendImageComponent (
                image = image,
            )
        }
        Text(
            text = "$firstname $lastname",
            style = labelStyle,
            color = LightColorScheme.onBackground,
            fontSize = 13.sp,
        )
    }
}