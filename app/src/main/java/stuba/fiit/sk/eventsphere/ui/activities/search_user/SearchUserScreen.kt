package stuba.fiit.sk.eventsphere.ui.activities.search_user

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.model.observeLiveData
import stuba.fiit.sk.eventsphere.ui.components.FriendBox
import stuba.fiit.sk.eventsphere.ui.components.SearchBarComponent
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

        SearchFriendTopBar (
            toProfile = toProfile
        )

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer (
                modifier = Modifier.height(25.dp)
            )

            Text(
                text = stringResource(id = R.string.search_for_user),
                style = welcomeStyle,
                fontSize = 25.sp
            )
            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            SearchBarComponent (
                onUpdate = {
                           searchUserViewModel.updateSearch(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer (
                modifier = Modifier.height(25.dp)
            )

        }
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp, 15.dp)
                .verticalScroll(rememberScrollState()),
        ) {

            val friends = observeLiveData(searchUserViewModel.friends)

            if (friends?.listFriends?.isEmpty() == true) {
                Text (
                    text = stringResource(id = R.string.friends_not_found),
                    style = welcomeStyle,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else {
                friends?.listFriends?.forEach { friend ->
                    val firstname = if (friend.firstname == null) stringResource(id = R.string.firstname) else friend.firstname ?: ""
                    val lastname = if (friend.lastname == null) stringResource(id = R.string.lastname) else friend.lastname ?: ""
                    val id = friend.id ?: 0
                    FriendBox (
                        firstname = firstname,
                        lastname =lastname,
                        onClick = toFriends,
                        id = id,
                        image = friend.profile_picture
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun SearchFriendTopBar (
    toProfile: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.top_bar),
            contentDescription = "welcome_background",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
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
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background),
                    contentDescription = "Back"
                )
            }
        }
    }
}