package stuba.fiit.sk.eventsphere.ui.activities.edit_profile

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.model.detector
import stuba.fiit.sk.eventsphere.model.model
import stuba.fiit.sk.eventsphere.model.observeLiveData
import stuba.fiit.sk.eventsphere.ui.components.AlertDialogComponent
import stuba.fiit.sk.eventsphere.ui.components.InputFieldComponent
import stuba.fiit.sk.eventsphere.ui.components.ProfileImageComponent
import stuba.fiit.sk.eventsphere.ui.components.SmallButtonComponent
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.EditProfileViewModel
import stuba.fiit.sk.eventsphere.viewmodel.MainViewModel
import java.io.IOException


@Composable
fun EditProfileScreen (
    back: () -> Unit,
    viewModel: MainViewModel,
    editProfileViewModel: EditProfileViewModel
) {

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
            var showError by remember { mutableStateOf(false) }
            
            val pickImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                if (uri != null) {
                    editProfileViewModel.userNewData.value?.profileImage = uri
                    try {
                        val inputStream = context.contentResolver.openInputStream(uri)
                        inputStream?.use { stream ->
                            val bitmap = BitmapFactory.decodeStream(stream)
                            val resizedBitmap = model.resizeBitmap(bitmap, 100)
                            editProfileViewModel.profileImage.value = resizedBitmap.asImageBitmap()
                        }
                        val inputImage = InputImage.fromBitmap(editProfileViewModel.profileImage.value?.asAndroidBitmap()!!, 0)
                        val result = detector.process(inputImage)
                            .addOnSuccessListener { faces ->
                                if (faces.isNotEmpty()) {
                                    editProfileViewModel.canBeProfilePictureAdded = true
                                } else {
                                    editProfileViewModel.canBeProfilePictureAdded = false
                                    showError = true
                                }
                            }
                            .addOnFailureListener { e ->
                                e.printStackTrace()
                            }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            if (showError) {
                AlertDialogComponent(
                    onDismissRequest = {},
                    onConfirmation = {
                        showError = false
                    },
                    onConfirmText = stringResource(id = R.string.change_image),
                    onDismissText = "",
                    dialogText = stringResource(id = R.string.error_image),
                    dialogTitle = stringResource(id = R.string.error_image_title)
                )
            }

            Box (
                modifier = Modifier
                    .clickable {
                        pickImageLauncher.launch("image/")
                    },
                contentAlignment = Alignment.Center
            ) {

                val image = observeLiveData(liveData = editProfileViewModel.profileImage)
                ProfileImageComponent (
                    image = image
                )
                Box(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .border(
                            3.dp,
                            shape = RoundedCornerShape(75.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                        .size(120.dp),
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
                    text = editProfileViewModel.userNewData.value?.email.toString(),
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
            val openErrorDialog = remember { mutableStateOf(false) }
            val error = remember { mutableStateOf("") }
            val context = LocalContext.current
            SmallButtonComponent(
                onClick = {
                    editProfileViewModel.viewModelScope.launch {

                        val (result, message) = editProfileViewModel.updateProfileOnSave(context)
                        error.value = message
                        if (result) {
                            openSaveDialog.value = true
                        } else {
                            openErrorDialog.value = true
                        }
                    }
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
            if(openErrorDialog.value) {
                AlertDialogComponent(
                    onDismissRequest = { openErrorDialog.value = false },
                    onConfirmation = {
                        openErrorDialog.value = false
                    },
                    dialogTitle = "Error",
                    dialogText = error.value,
                    onDismissText = "",
                    onConfirmText = stringResource(id = R.string.ok)
                )
            }

        }
    }
}