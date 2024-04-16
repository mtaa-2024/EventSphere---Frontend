package stuba.fiit.sk.eventsphere.ui.activities.edit_profile

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
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.ui.components.InputField
import stuba.fiit.sk.eventsphere.ui.components.InputPasswordField
import stuba.fiit.sk.eventsphere.ui.components.PrimaryButton
import stuba.fiit.sk.eventsphere.ui.theme.labelStyle
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.EditProfileViewModel
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel
import stuba.fiit.sk.eventsphere.viewmodel.updateProfileClass

@Composable
fun EditProfileScreen (
    toProfile: () -> Unit,
    viewModel: MainViewModel,
    editProfileViewModel: EditProfileViewModel
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
                TextButton(
                    onClick = {
                        editProfileViewModel.viewModelScope.launch {
                            if (editProfileViewModel.updateProfileInfo(
                                    viewModel.loggedUser.value?.id?: 0,
                                    editProfileViewModel.userNewData.value?.firstname.toString(),
                                    editProfileViewModel.userNewData.value?.lastname.toString(),
                                    editProfileViewModel.userNewData.value?.oldEmail.toString(),
                                    editProfileViewModel.userNewData.value?.newEmail.toString()
                                )
                            ) {
                                viewModel.viewModelScope.launch {
                                    viewModel.updateUser()
                                }
                            }
                        }
                        toProfile()
                    }
                ) {
                    Text(
                        text = "Save",
                        style = labelStyle,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

            }
        }
        val scroll = rememberScrollState()
        Column (
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
            .verticalScroll(scroll),
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
                fontSize = 20.sp
            )

            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )

            Column(
                modifier = Modifier
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

            }
            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )
            InputField(
                label = "Firstname",
                value = firstName,
                onChange = editProfileViewModel::updateFirstname
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            InputField(
                label = "Lastname",
                value = lastName,
                onChange = editProfileViewModel::updateLastname
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            Text(
                text = "Email",
                style = welcomeStyle,
                fontSize = 25.sp
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            InputField(
                label = "Old Email",
                value = "Old Email",
                onChange = editProfileViewModel::updateOldEmail
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            InputField(
                label = "New Email",
                value = "New Email",
                onChange = editProfileViewModel::updateNewEmail
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            Text(
                text = "Password",
                style = welcomeStyle,
                fontSize = 25.sp
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            InputPasswordField(
                label = "Old password",
                value = "Old password",
                onChange = editProfileViewModel::updateOldPassword
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            InputPasswordField(
                label = "New password",
                value = "New password",
                onChange = editProfileViewModel::updateNewPassword
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )
        }
    }
}