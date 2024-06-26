package stuba.fiit.sk.eventsphere.ui.activities.friend

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.model.observeLiveData
import stuba.fiit.sk.eventsphere.ui.components.ProfileImageComponent
import stuba.fiit.sk.eventsphere.ui.components.SmallButtonComponent
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.FriendsViewModel
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun FriendsScreen (
    toProfile: () -> Unit,
    viewModel: MainViewModel,
    friendsViewModel: FriendsViewModel
) {
    if (friendsViewModel.canBeAdded.isInitialized) {
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
                        onClick = toProfile,
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
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
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(
                        modifier = Modifier
                            .height(30.dp)
                    )

                    ProfileImageComponent(
                        image = friendsViewModel.friendData.profileImage
                    )

                    Spacer(
                        modifier = Modifier
                            .height(15.dp)
                    )

                    val firstName =
                        if (friendsViewModel.friendData.firstname == null) stringResource(id = R.string.firstname) else friendsViewModel.friendData.firstname
                            ?: ""
                    val lastName =
                        if (friendsViewModel.friendData.lastname == null) stringResource(id = R.string.lastname) else friendsViewModel.friendData.lastname
                            ?: ""

                    Text(
                        text = "$firstName $lastName",
                        style = welcomeStyle,
                        fontSize = 20.sp,
                    )

                    Spacer(
                        modifier = Modifier.height(25.dp)
                    )


                    val selected = observeLiveData(liveData = friendsViewModel.canBeAdded)

                    SmallButtonComponent(
                        text = if (selected == true) stringResource(id = R.string.add_friend) else stringResource(
                            id = R.string.you_are_friends
                        ),
                        isSelected = selected != true,
                        onClick = {
                            friendsViewModel.viewModelScope.launch {
                                friendsViewModel.addAsFriend()
                            }
                        }
                    )
                }
            }
        }
    }
}