package stuba.fiit.sk.eventsphere.ui.activities.profile


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.model.observeLiveData
import stuba.fiit.sk.eventsphere.ui.components.ButtonComponent
import stuba.fiit.sk.eventsphere.ui.components.FriendBox
import stuba.fiit.sk.eventsphere.ui.components.FriendImageComponent
import stuba.fiit.sk.eventsphere.ui.components.ProfileImageComponent
import stuba.fiit.sk.eventsphere.ui.components.SmallButtonComponent
import stuba.fiit.sk.eventsphere.ui.theme.labelStyle
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel
import stuba.fiit.sk.eventsphere.viewmodel.ProfileViewModel
import java.util.Locale


@Composable
fun ProfileScreen (
    toLogout: () -> Unit,
    back: () -> Unit,
    toEventCenter: () -> Unit,
    toEditProfile: () -> Unit,
    toFriend: (id:Int?) -> Unit,
    toSearchUser: ()-> Unit,
    viewModel: MainViewModel,
    profileViewModel: ProfileViewModel,
    onLanguageChange: (Locale) -> Unit,
    onThemeChange: (Boolean) -> Unit
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

            val firstName = if (viewModel.loggedUser.value?.firstname == null) stringResource(id = R.string.firstname) else viewModel.loggedUser.value?.firstname ?: ""
            val lastName = if (viewModel.loggedUser.value?.lastname == null) stringResource(id = R.string.lastname) else viewModel.loggedUser.value?.lastname ?: ""

            Text(
                text = "$firstName $lastName",
                style = welcomeStyle,
                color = MaterialTheme.colorScheme.onBackground,
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
                    text = stringResource(id = R.string.friends),
                    style = welcomeStyle,
                    fontSize = 20.sp
                )

                SmallButtonComponent (
                    text = stringResource(id = R.string.find_friend),
                    isSelected = false,
                    onClick = { toSearchUser() }
                )

            }

            val friendScrollState = rememberScrollState()

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .horizontalScroll(friendScrollState),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                val friends = observeLiveData(profileViewModel.friends)

                if (friends?.listFriends?.isEmpty() == true) {
                    Text (
                        text = stringResource(id = R.string.no_friends),
                        style = welcomeStyle,
                        fontSize = 18.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                } else {
                    friends?.listFriends?.forEach { friend ->
                        val firstname = if (friend.firstname == null) stringResource(id = R.string.firstname) else friend.firstname ?: ""
                        val lastname = if (friend.lastname == null) stringResource(id = R.string.firstname) else friend.lastname ?: ""
                        val id = friend.id ?: 0
                        FriendBox (
                            firstname = firstname,
                            lastname =lastname,
                            onClick = toFriend,
                            id = id,
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
                    fillColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.background,
                    text = stringResource(id = R.string.event_center),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                )


                Spacer (
                    modifier = Modifier
                        .height(30.dp)
                )

                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .border(
                            BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
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
                            text = stringResource(id = R.string.settings),
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
                                    text = stringResource(id = R.string.notification),
                                    style = labelStyle,
                                    fontSize = 18.sp
                                )
                                Spacer (
                                    modifier = Modifier
                                        .height(10.dp)
                                )
                                Text(
                                    text = stringResource(id = R.string.darkmode),
                                    style = labelStyle,
                                    fontSize = 18.sp
                                )
                                Spacer (
                                    modifier = Modifier
                                        .height(10.dp)
                                )
                                Text(
                                    text = stringResource(id = R.string.language),
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

                                val darkMode = isSystemInDarkTheme()
                                var modeChange by remember { mutableStateOf(darkMode) }


                                SmallButtonComponent(
                                    text = "mode",
                                    isSelected = false,
                                    onClick = {
                                        modeChange = !modeChange
                                        onThemeChange(modeChange)
                                    }
                                )

                                println(modeChange)

                                Spacer (
                                    modifier = Modifier
                                        .height(10.dp)
                                )

                                var language by remember { mutableStateOf("en") }
                                SmallButtonComponent(
                                    text = if (language == "en") "English" else "Slovak",
                                    isSelected = false,
                                    onClick = {
                                        if (language == "en") language = "sk" else
                                        if (language == "sk") language = "en"
                                        onLanguageChange(Locale(language))
                                    }
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
                text = stringResource(id = R.string.logout),
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
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background),
                    contentDescription = "Back"
                )
            }
            SmallButtonComponent (
                text = stringResource(id = R.string.edit_profile),
                isSelected = false,
                onClick = { toEditProfile() }
            )
        }
    }
}