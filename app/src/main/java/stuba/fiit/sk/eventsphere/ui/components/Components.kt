package stuba.fiit.sk.eventsphere.ui.components

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.model.AlarmReceiver
import stuba.fiit.sk.eventsphere.model.DateInput
import stuba.fiit.sk.eventsphere.model.Event
import stuba.fiit.sk.eventsphere.model.LocationData
import stuba.fiit.sk.eventsphere.model.User
import stuba.fiit.sk.eventsphere.model.observeLiveData
import stuba.fiit.sk.eventsphere.ui.theme.buttonStyle
import stuba.fiit.sk.eventsphere.ui.theme.labelStyle
import stuba.fiit.sk.eventsphere.ui.theme.paragraph
import stuba.fiit.sk.eventsphere.ui.theme.smallButton
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import stuba.fiit.sk.eventsphere.viewmodel.CreateEventViewModel
import stuba.fiit.sk.eventsphere.viewmodel.Events
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID
import kotlin.reflect.KSuspendFunction1


@Composable
fun FriendBox (
    firstname: String,
    lastname: String,
    image: ImageBitmap?,
    onClick: (User) -> Unit,
    user: User
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box (
            modifier = Modifier
                .clickable(
                    onClick = { onClick(user) }
                ),
            contentAlignment = Alignment.Center
        ) {
            FriendImageComponent (
                image = image,
            )
        }
        Spacer (
            modifier = Modifier.height(5.dp)
        )
        Text(
            text = "$firstname $lastname",
            style = labelStyle,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 13.sp,
        )
    }
}

@Composable
fun ProfileImageComponent (
    image: ImageBitmap?
) {
    Box (
        contentAlignment = Alignment.Center
    ) {
        if (image != null) {
            Image(
                painter = BitmapPainter(image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(117.dp)
                    .clip(CircleShape)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.profile_default),
                contentDescription = "Profile background",
                modifier = Modifier.size(117.dp)
            )
        }
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
}


@Composable
fun TopBarProfileComponent (
    image: ImageBitmap?,
) {
    Box (
        contentAlignment = Alignment.Center
    ) {
        if (image != null) {
            Image(
                painter = BitmapPainter(image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(69.dp)
                    .clip(CircleShape)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.profile_default),
                contentDescription = "Profile background",
                modifier = Modifier.size(69.dp)
            )
        }
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .border(
                    1.dp,
                    shape = RoundedCornerShape(75.dp),
                    color = MaterialTheme.colorScheme.background
                )
                .size(70.dp),
        )
    }
}

@Composable
fun FriendImageComponent (
    image: ImageBitmap?,
) {
    Box (
        contentAlignment = Alignment.Center
    ) {
        if (image != null) {
            Image(
                painter = BitmapPainter(image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.profile_default),
                contentDescription = "Profile background",
                modifier = Modifier.size(68.dp)
            )
        }
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .border(
                    2.dp,
                    shape = RoundedCornerShape(75.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                .size(70.dp),
        )
    }
}

@Composable
fun ButtonComponent (
    onClick: () -> Unit,
    fillColor: Color,
    textColor: Color,
    text: String,
    modifier: Modifier,
) {
    ElevatedButton (
        modifier = modifier
            .shadow(3.dp, shape = shape, clip = false),
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(fillColor),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
    ) {
        Text (
            text = text,
            style = buttonStyle,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            letterSpacing = 0.sp,
            color = textColor
        )
    }
}

@Composable
fun SearchBarComponent (
    onUpdate: (String) -> Unit,
    modifier: Modifier
) {
    val search = stringResource(id = R.string.search)
    var value by remember { mutableStateOf(search) }
    var isFocused by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val maxCharacters = 40

    TextField (
        modifier = modifier
            .height(50.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .border(
                1.dp,
                shape = RoundedCornerShape(15.dp),
                color = MaterialTheme.colorScheme.primary
            )
            .onFocusChanged { isFocused = it.isFocused },
        value = if (isFocused && value == "Search") "" else value,
        singleLine = false,
        onValueChange = {
            if (it.length < maxCharacters) {
                value = it
                onUpdate(value)
            }
        },
        textStyle = labelStyle.copy(
            fontSize = 16.sp,
            letterSpacing = 0.sp,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        leadingIcon = { IconButton (
            onClick = {  },
        ) {
            Icon (
                painterResource(id = R.drawable.search),
                contentDescription = "Icon"
            )
        } },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = Color.Gray,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
        ),
    )

}

@Composable
fun InputCommentFieldComponent (
    onUpdate: (String) -> Unit,
    comment: String,
    modifier: Modifier
) {
    var value by remember { mutableStateOf(comment) }
    var isFocused by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField (
        modifier = modifier
            .height(IntrinsicSize.Min)
            .onFocusChanged { isFocused = it.isFocused },
        value = value,
        label = { Text(text = "") },
        singleLine = false,
        onValueChange = {
            value = it
            onUpdate(value)
        },
        textStyle = labelStyle.copy(
            fontSize = 16.sp,
            letterSpacing = 0.sp,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = Color.Gray,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground
        ),
    )
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun InputFieldComponent (
    onUpdate: (String) -> Unit,
    onCheck: KSuspendFunction1<String, String>?,
    label: String,
    text: String,
    keyboardType: KeyboardType,
    modifier: Modifier
) {
    var value by remember { mutableStateOf(text) }
    var message by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    var passwordVisible by remember { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()

    if (!isFocused) {
        coroutineScope.launch {
            if (onCheck != null && value.length > 1) {
                message = onCheck(value)
            }
        }
    }

    OutlinedTextField (
        modifier = modifier
            .height(IntrinsicSize.Min)
            .onFocusChanged { isFocused = it.isFocused },
        value = value,
        label = { Text(
                    text = label,
                    style = labelStyle,
                    fontSize = 14.sp,
                    letterSpacing = 1.sp
                )
        },
        singleLine = false,
        onValueChange = {
            value = it
            onUpdate(value)
            if (passwordVisible) passwordVisible = false
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else if (keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None,
        textStyle = labelStyle.copy(
            fontSize = 16.sp,
            letterSpacing = if (keyboardType == KeyboardType.Password && !passwordVisible) 2.sp else 0.sp,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        trailingIcon = { if(keyboardType == KeyboardType.Password) IconButton (
            onClick = { passwordVisible = !passwordVisible },
            ) {
            Icon (
                painterResource(id = R.drawable.eye),
                contentDescription = "Icon"
            )
        } },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Done),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = Color.Gray,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
            focusedLabelColor = MaterialTheme.colorScheme.primary
        ),
    )
    if (message != "") {
        Text (
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = paragraph,
            fontSize = 14.sp,
            modifier = Modifier.padding(5.dp)
        )
    }
}



@Composable
fun CategoryBox (
    icon: Int,
    initializeState: Boolean,
    onClick: (Boolean) -> Unit,
) {
    var isSelected by remember { mutableStateOf(initializeState) }

    Box (
        modifier = Modifier
            .width(48.dp)
            .height(48.dp)
            .clip(
                RoundedCornerShape(15.dp)
            )
            .border(
                3.dp,
                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                RoundedCornerShape(15.dp)
            )
            .background(MaterialTheme.colorScheme.background)
            .padding(2.dp)
            .clickable(
                onClick = {
                    isSelected = !isSelected
                    onClick(isSelected)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(
                id = icon
            ),
            contentDescription = "icon",
            contentScale = ContentScale.Inside,
            colorFilter = ColorFilter.tint(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
fun SmallButtonComponent (
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .width(110.dp)
            .height(30.dp)
            .clip(RoundedCornerShape(15.dp))
            .border(
                1.dp,
                if (isSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary,
                RoundedCornerShape(15.dp)
            )
            .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background)
            .clickable {
                if (!isSelected)
                    onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text (
            text = text,
            fontSize = 14.sp,
            style = smallButton,
            color = if (isSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun EventBanner (
    event: Event,
    title: String,
    date: String,
    location: String,
    icon: Int,
    toEvent: (event: Event) -> Unit
) {
    Box (
        modifier = Modifier
            .width(350.dp)
            .clickable { toEvent(event) }
    ) {
        Box (
            modifier = Modifier
                .width(320.dp)
                .height(80.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 15.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 15.dp
                    )
                )
                .background(MaterialTheme.colorScheme.primary),
        ) {
            Column (
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text (
                    text = title,
                    style = labelStyle,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.background
                )
                Text (
                    text = date,
                    style = smallButton,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.background
                )
                Text (
                    text = location,
                    style = smallButton,
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.background
                )
            }
        }
        Box (
            modifier = Modifier
                .width(60.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .align(Alignment.CenterEnd),
            contentAlignment = Alignment.Center
        ) {
            Image (
                painter = painterResource(id = icon),
                contentDescription = "event_icon",
                contentScale = ContentScale.Inside,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background)
            )
        }
    }
}

@Composable
fun CommentProfileImage (
    image: ImageBitmap?
) {
    Box (
        contentAlignment = Alignment.Center
    ) {
        if (image != null) {
            Image(
                painter = BitmapPainter(image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(39.dp)
                    .clip(CircleShape)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.profile_default),
                contentDescription = "Profile background",
                modifier = Modifier.size(39.dp)
            )
        }
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .border(
                    1.dp,
                    shape = RoundedCornerShape(75.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                .size(40.dp),
        )
    }
}

@Composable
fun CommentBanner (
    firstname: String,
    lastname: String,
    text: String,
    image: ImageBitmap?,
    onPublish: (String) -> Unit,
    isForPublish: Boolean,
) {
    val forPublish by remember { mutableStateOf(isForPublish) }
    var value by remember { mutableStateOf(text) }

    Column (
        modifier = Modifier
            .width(320.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .width(280.dp)
                    .height(110.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(MaterialTheme.colorScheme.primary)
                    .align(Alignment.CenterEnd)
            ) {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .matchParentSize()
                        .verticalScroll(scrollState)
                        .padding(35.dp, 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$firstname $lastname",
                        style = labelStyle,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.background
                    )
                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                    )
                    if (forPublish) {
                        InputCommentFieldComponent(
                            onUpdate = { input ->
                                       value = input
                            },
                            modifier = Modifier,
                            comment = value
                        )
                    } else {
                        Text(
                            text = text,
                            style = labelStyle,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .align(Alignment.TopStart),
                contentAlignment = Alignment.Center
            ) {
                CommentProfileImage(image = image)
            }
        }
        Spacer (
            modifier = Modifier
                .height(10.dp)
        )
        if (isForPublish) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 0.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (forPublish) {
                    SmallButtonComponent(
                        text = "Publish",
                        isSelected = false,
                        onClick = {
                            onPublish(value)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun MapLocationPicker (
    onAdd: (input: LocationData) -> Unit,
    longitude: Double,
    latitude: Double,
    properties: MapProperties,
    uiSettings: MapUiSettings,
    isForPicking: Boolean
) {
    val context = LocalContext.current
    var userLocation by remember { mutableStateOf<LatLng?>(LatLng(latitude, longitude)) }
    var address by remember { mutableStateOf("") }

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val cameraPositionState = rememberCameraPositionState { position = CameraPosition.fromLatLngZoom(userLocation ?: LatLng(0.0, 0.0), 12f) }

    if (isForPicking) {
        val locationPermissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted: Boolean ->
                if (isGranted) {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        if (location != null) {
                            userLocation = LatLng(location.latitude, location.longitude)
                            cameraPositionState.position =
                                CameraPosition.fromLatLngZoom(userLocation!!, 12f)
                        }
                    }
                }
            }
        )

        LaunchedEffect(true) {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) -> {
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }

                else -> {
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        }

        LaunchedEffect(userLocation) {
            userLocation?.let {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                if (addresses?.isNotEmpty() == true) {
                    address = addresses[0].getAddressLine(0) ?: ""
                }
            }
        }
    }
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userLocation != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth(),
                    cameraPositionState = cameraPositionState,
                    properties = properties,
                    uiSettings = uiSettings,
                    onMapClick = { latLng ->
                        if (isForPicking)
                            userLocation = latLng
                    }
                ) {
                    userLocation?.let { latLng ->
                        Marker(
                            state = MarkerState(position = latLng),
                            title = if (isForPicking) "Your location" else "Event location"
                        )
                    }

                }
            }
        } else {
            Text(text = "Loading map")
        }
        if (isForPicking) {
            Spacer(
                modifier = Modifier
                    .height(50.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                SmallButtonComponent(
                    text = "Reset",
                    isSelected = false,
                    onClick = {
                        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                            if (location != null) {
                                userLocation = LatLng(location.latitude, location.longitude)
                                cameraPositionState.position =
                                    CameraPosition.fromLatLngZoom(userLocation!!, 12f)
                            }
                        }
                    }
                )
                SmallButtonComponent(
                    text = "Add location",
                    isSelected = false,
                    onClick = {
                        onAdd(
                            LocationData(
                                address = if (address.contains(",")) address.substring(address.indexOf(",") + 1, address.indexOf(",", address.indexOf(",") + 1)).trim() else address,
                                latitude = userLocation?.latitude ?: 0.0,
                                longitude = userLocation?.longitude ?: 0.0,
                            )
                        )
                    }
                )
            }
            Spacer(
                modifier = Modifier
                    .height(50.dp)
            )
        }
    }
}

@Composable
fun AlertDialogComponent(
    onDismissRequest: () -> Unit,
    onDismissText: String,
    onConfirmation: () -> Unit,
    onConfirmText: String,
    dialogTitle: String,
    dialogText: String
) {
    AlertDialog (
        title = {
            Text (
                text = dialogTitle,
                style = welcomeStyle,
                fontSize = 24.sp,
                letterSpacing = 1.sp
            )
        },
        text = {
            Text (
                text = dialogText,
                style = paragraph,
                fontSize = 15.sp
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(
                    text = onConfirmText,
                    style = paragraph,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(
                    text = onDismissText,
                    style = paragraph,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}

@Composable
fun DateTimePicker(
    updateDate: (year: Int, month: Int, day: Int) -> Unit,
    updateTime: (hours: Int, minutes: Int) -> Unit,
    date: String?
){
    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mHours: Int
    val mMinutes: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH) + 1
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mHours = mCalendar.get(Calendar.HOUR_OF_DAY)
    mMinutes = mCalendar.get(Calendar.MINUTE)

    mCalendar.time = Date()
    val mDate = remember { mutableStateOf("$mDay/$mMonth/$mYear") }
    val mTime = remember { mutableStateOf("$mHours:$mMinutes") }

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
            mDate.value = "${mDay}/${mMonth+1}/$mYear"
            updateDate(mYear, mMonth, mDay)
        }, mYear, mMonth, mDay
    )

    val mTimePickerDialog = TimePickerDialog (
        mContext,
        { _: TimePicker, mHours: Int, mMinutes: Int ->
            mTime.value = "$mHours:$mMinutes"
            updateTime(mHours, mMinutes)
        }, mHours, mMinutes, true
    )

    Column (
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box (
            modifier = Modifier.clickable(onClick = {
                mTimePickerDialog.show()
                mDatePickerDialog.show()
            })
        ) {
            Image (
                painter = painterResource(id = R.drawable.icon),
                contentDescription = "Location picker" 
            )
        }
        
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = date ?: "${mDate.value} ${mTime.value}",
            modifier = Modifier,
            style = labelStyle,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.background
        )
    }
}

@SuppressLint("ScheduleExactAlarm")
fun scheduleNotification(context: Context, delay: Long) {
    Log.d("MainActivity", "scheduleNotification called with delay: $delay milliseconds")

    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    alarmManager.setExact(
        AlarmManager.RTC_WAKEUP,
        System.currentTimeMillis() + delay,
        pendingIntent
    )
}
