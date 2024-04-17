package stuba.fiit.sk.eventsphere.ui.activities.friends

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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.ui.components.PrimaryButton
import stuba.fiit.sk.eventsphere.ui.theme.buttonStyle
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.FriendsViewModel
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Composable
fun FriendsScreen (
    toProfile: () -> Unit,
    viewModel: MainViewModel,
    friendsViewModel: FriendsViewModel
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
        Column (
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Column (
                modifier = Modifier
                    .fillMaxSize(),
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
                val firstName = friendsViewModel.friend.value?.firstname ?: "Firstname"
                val lastName = friendsViewModel.friend.value?.lastname ?: "Lastname"

                Text(
                    text = "$firstName $lastName",
                    style = welcomeStyle,
                    fontSize = 20.sp,
                )

                //doobre tak toto treba porobit xDD
                TextButton(
                    onClick = {
                        viewModel.viewModelScope.launch {
                            friendsViewModel.addFriend( friendsViewModel.friend.value?.id ?:0, viewModel.loggedUser.value?.id ?:0)
                        }
                    }
                ) {
                    Text(
                        text = "Add friend",
                        style = buttonStyle,
                        fontSize = 20.sp
                    )
                }

            }

/*
            PrimaryButton (text = "addFriend", onClick = {
                viewModel.viewModelScope.launch {
                    friendsViewModel.addFriend( friendsViewModel.friend.value?.id ?:0, viewModel.loggedUser.value?.id ?:0)
                }
            })*/
        }
    }
}