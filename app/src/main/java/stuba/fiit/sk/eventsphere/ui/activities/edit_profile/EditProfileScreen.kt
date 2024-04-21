package stuba.fiit.sk.eventsphere.ui.activities.edit_profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.ui.activities.login.LoginScreen
import stuba.fiit.sk.eventsphere.ui.components.AlertDialogComponent
import stuba.fiit.sk.eventsphere.ui.components.InputFieldComponent
import stuba.fiit.sk.eventsphere.ui.components.SmallButtonComponent
import stuba.fiit.sk.eventsphere.ui.theme.LightColorScheme
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.EditProfileViewModel
import stuba.fiit.sk.eventsphere.viewmodel.LoginViewModel
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel

@Preview(showBackground = true, locale = "sk")
@Composable
fun Preview () {
    EditProfileScreen(back = {}, viewModel = MainViewModel(), editProfileViewModel = EditProfileViewModel())
}

@Composable
fun EditProfileScreen (
    back: () -> Unit,
    viewModel: MainViewModel,
    editProfileViewModel: EditProfileViewModel
) {
    editProfileViewModel.userNewData.value?.firstname = stringResource(id = R.string.enter_firstname)
    editProfileViewModel.userNewData.value?.lastname = stringResource(id = R.string.enter_lastname)
    editProfileViewModel.userNewData.value?.oldEmail = stringResource(id = R.string.enter_old_email)
    editProfileViewModel.userNewData.value?.newEmail = stringResource(id = R.string.enter_new_email)
    editProfileViewModel.userNewData.value?.oldPassword = stringResource(id = R.string.enter_old_password)
    editProfileViewModel.userNewData.value?.newPassword = stringResource(id = R.string.enter_new_password)


    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EditProfileTopBar(
            back = back,
            viewModel = viewModel,
            editProfileViewModel = editProfileViewModel
        )

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )


            val context = LocalContext.current
            val pickImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                suspend { editProfileViewModel.uriToByteArray(context, uri, viewModel.loggedUser.value?.id ?: 0) }
            }


            Box (
                modifier = Modifier
                    .clickable {
                        pickImageLauncher.launch("image/")
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profilescreenbackground),
                    contentDescription = "Profile background",
                    contentScale = ContentScale.Inside,
                    colorFilter = ColorFilter.tint(LightColorScheme.primary)
                )

                Image(
                    painter = painterResource(id = R.drawable.profilescreenbackground),
                    contentDescription = "Profile background",
                    colorFilter = ColorFilter.tint(LightColorScheme.background),
                    modifier = Modifier.size(140.dp, 140.dp)
                )
            }


            Spacer(
                modifier = Modifier
                    .height(5.dp)
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
                    .height(30.dp)
            )


            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp, 0.dp)
            ) {

                InputFieldComponent(
                    label = stringResource(id = R.string.firstname),
                    text = editProfileViewModel.userNewData.value?.firstname.toString(),
                    onUpdate = editProfileViewModel::updateFirstname,
                    keyboardType = KeyboardType.Text,
                    onCheck = null,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier
                        .height(15.dp)
                )

                InputFieldComponent(
                    label = stringResource(id = R.string.lastname),
                    text = editProfileViewModel.userNewData.value?.lastname.toString(),
                    onUpdate = editProfileViewModel::updateLastname,
                    keyboardType = KeyboardType.Text,
                    onCheck = null,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier
                        .height(25.dp)
                )

                InputFieldComponent(
                    label = stringResource(id = R.string.old_email),
                    text = editProfileViewModel.userNewData.value?.oldEmail.toString(),
                    onUpdate = editProfileViewModel::updateOldEmail,
                    keyboardType = KeyboardType.Text,
                    onCheck = null,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier
                        .height(15.dp)
                )

                InputFieldComponent(
                    label = stringResource(id = R.string.new_email),
                    text = editProfileViewModel.userNewData.value?.newEmail.toString(),
                    onUpdate = editProfileViewModel::updateNewEmail,
                    keyboardType = KeyboardType.Text,
                    onCheck = null,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier
                        .height(25.dp)
                )

                InputFieldComponent(
                    label = stringResource(id = R.string.old_password),
                    text = editProfileViewModel.userNewData.value?.oldPassword.toString(),
                    onUpdate = editProfileViewModel::updateOldPassword,
                    keyboardType = KeyboardType.Password,
                    onCheck = null,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier
                        .height(15.dp)
                )

                InputFieldComponent(
                    label = stringResource(id = R.string.new_password),
                    text = editProfileViewModel.userNewData.value?.newPassword.toString(),
                    onUpdate = editProfileViewModel::updateNewPassword,
                    keyboardType = KeyboardType.Password,
                    onCheck = null,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun EditProfileTopBar (
    back: () -> Unit,
    editProfileViewModel: EditProfileViewModel,
    viewModel: MainViewModel
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
                onClick = back,
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "Back"
                )
            }
            val openSaveDialog = remember { mutableStateOf(false) }
            SmallButtonComponent(
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
                    openSaveDialog.value = true
                },
                text = stringResource(id = R.string.save),
                isSelected = false
            )
            if(openSaveDialog.value) {
                AlertDialogComponent(
                    onDismissRequest = { openSaveDialog.value = false },
                    onConfirmation = {
                        openSaveDialog.value = false
                        back()
                    },
                    dialogTitle = stringResource(id = R.string.save_dialog_label),
                    dialogText = "",
                    onDismissText = "",
                    onConfirmText = stringResource(id = R.string.ok)
                )
            }

        }
    }
}