package stuba.fiit.sk.eventsphere.ui.activities.search_user

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.ui.activities.profile.FriendBox
import stuba.fiit.sk.eventsphere.ui.components.InputFieldComponent
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel
import stuba.fiit.sk.eventsphere.viewmodel.SearchUserViewModel

@Composable
fun SearchUserScreen (
    toProfile: () -> Unit,
    toFriends: (id:Int?) -> Unit,
    viewModel: MainViewModel,
    searchUserViewModel: SearchUserViewModel
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
        ) {
            Text(
                text = "Search User",
                style = welcomeStyle,
                fontSize = 25.sp
            )
            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            InputFieldComponent(
                label = "Search",
                text = searchUserViewModel.search.value.toString(),
                onUpdate = searchUserViewModel::updateSearch,
                keyboardType = KeyboardType.Text,
                onCheck = null,
                modifier = Modifier
                    .fillMaxWidth()
            )
            /*Button(
                onClick = {
                    viewModel.viewModelScope.launch {
                        searchUserViewModel.getFriendsSearch(searchUserViewModel.search.value.toString())

                    }
                }) {
                Text(text = "search")
            }*/

        }
        val friendScrollState = rememberScrollState()
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .verticalScroll(friendScrollState),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                searchUserViewModel.friends.value?.listFriends?.forEach { friend ->
                    Text(text = friend.firstname!!
                    )

                    if (friend.firstname != null && friend.lastname != null) {
                        FriendBox(
                            firstname = friend.firstname!!,
                            lastname = friend.lastname!!,
                            onClick = toFriends,
                            id = friend.id!!,
                            image = friend.profile_picture
                        )
                }
            }
        }
    }
}