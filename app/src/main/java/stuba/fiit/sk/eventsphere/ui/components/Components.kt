package stuba.fiit.sk.eventsphere.ui.components

import android.Manifest
import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.location.Geocoder
import android.widget.DatePicker
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
import stuba.fiit.sk.eventsphere.model.DateInput
import stuba.fiit.sk.eventsphere.model.LocationData
import stuba.fiit.sk.eventsphere.ui.theme.LightColorScheme
import stuba.fiit.sk.eventsphere.ui.theme.buttonStyle
import stuba.fiit.sk.eventsphere.ui.theme.labelStyle
import stuba.fiit.sk.eventsphere.ui.theme.paragraph
import stuba.fiit.sk.eventsphere.ui.theme.smallButton
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import java.util.Locale
import kotlin.reflect.KSuspendFunction1


@Composable
fun ProfileImageComponent (
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
                    .size(100.dp)
                    .clip(CircleShape)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.profile_default),
                contentDescription = "Profile background",
                modifier = Modifier.size(100.dp)
            )
        }
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .border(3.dp, shape = RoundedCornerShape(75.dp), color = LightColorScheme.primary)
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
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .size(65.dp)
                    .clip(CircleShape)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.profile_default),
                contentDescription = "Profile background",
                modifier = Modifier.size(65.dp)
            )
        }
        Box(
            modifier = Modifier
                .background(shape = RoundedCornerShape(75.dp), color = Color.Transparent)
                .border(
                    2.dp,
                    shape = RoundedCornerShape(75.dp),
                    color = LightColorScheme.background
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
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .size(65.dp)
                    .clip(CircleShape)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.profile_default),
                contentDescription = "Profile background",
                modifier = Modifier.size(65.dp)
            )
        }
        Box(
            modifier = Modifier
                .background(shape = RoundedCornerShape(75.dp), color = Color.Transparent)
                .border(2.dp, shape = RoundedCornerShape(75.dp), color = LightColorScheme.primary)
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
        border = BorderStroke(1.dp, LightColorScheme.primary),
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

@Preview
@Composable
fun preb() {
    SearchBarComponent(onUpdate = {}, modifier = Modifier)
}

@Composable
fun SearchBarComponent (
    onUpdate: (String) -> Unit,
    modifier: Modifier
) {
    var value by remember { mutableStateOf("Search") }
    var isFocused by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    TextField (
        modifier = modifier
            .height(50.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .border(1.dp, shape = RoundedCornerShape(15.dp), color = LightColorScheme.primary)
            .onFocusChanged { isFocused = it.isFocused },
        value = if (isFocused && value == "Search") "" else value,
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
            unfocusedBorderColor = LightColorScheme.primary,
            unfocusedContainerColor = LightColorScheme.background,
            focusedBorderColor = LightColorScheme.primary,
            focusedContainerColor = LightColorScheme.background,
            focusedTextColor = LightColorScheme.onBackground,
        ),
    )

}

@Composable
fun InputCommentFieldComponent (
    onUpdate: (String) -> Unit,
    modifier: Modifier
) {
    var value by remember { mutableStateOf("Insert you comment") }
    var isFocused by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField (
        modifier = modifier
            .height(IntrinsicSize.Min)
            .onFocusChanged { isFocused = it.isFocused },
        value = if (isFocused && value == "Insert you comment") "" else value,
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
            unfocusedBorderColor = LightColorScheme.primary,
            unfocusedContainerColor = LightColorScheme.primary,
            focusedBorderColor = LightColorScheme.primary,
            focusedContainerColor = LightColorScheme.primary,
            focusedTextColor = LightColorScheme.onBackground,
            unfocusedLabelColor = LightColorScheme.onBackground
        ),
    )
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun InputFieldComponent (
    onUpdate: (String) -> Unit,
    onCheck: KSuspendFunction1<String, Pair<Boolean, String>>?,
    label: String,
    text: String,
    keyboardType: KeyboardType,
    modifier: Modifier
) {
    var value by remember { mutableStateOf(text) }
    var isFocused by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    var passwordVisible by remember { mutableStateOf(true) }


    val coroutineScope = rememberCoroutineScope()

    if (!isFocused && value != text) {
        coroutineScope.launch {
            if (onCheck != null) {
                val (newError, newMessage) = onCheck(value)
                error = newError
                message = newMessage
            }
        }
    }

    OutlinedTextField (
        modifier = modifier
            .height(IntrinsicSize.Min)
            .onFocusChanged { isFocused = it.isFocused },
        value = if (isFocused && value == text) "" else value,
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
            unfocusedBorderColor = LightColorScheme.primary,
            unfocusedContainerColor = LightColorScheme.background,
            focusedBorderColor = LightColorScheme.primary,
            focusedContainerColor = LightColorScheme.background,
            focusedTextColor = LightColorScheme.onBackground,
            unfocusedLabelColor = LightColorScheme.onBackground,
            focusedLabelColor = LightColorScheme.primary
        ),
    )
    if (error) {
        Text (
            text = message,
            color = LightColorScheme.error,
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
                if (isSelected) LightColorScheme.primary else LightColorScheme.primaryContainer,
                RoundedCornerShape(15.dp)
            )
            .background(LightColorScheme.background)
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
            colorFilter = ColorFilter.tint(if (isSelected) LightColorScheme.primary else LightColorScheme.primaryContainer)
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
            .width(100.dp)
            .height(30.dp)
            .clip(RoundedCornerShape(15.dp))
            .border(
                1.dp,
                if (isSelected) LightColorScheme.background else LightColorScheme.primary,
                RoundedCornerShape(15.dp)
            )
            .background(if (isSelected) LightColorScheme.primary else LightColorScheme.background)
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
            color = if (isSelected) LightColorScheme.background else LightColorScheme.onBackground
        )
    }
}

@Composable
fun EventBanner (
    id: Int,
    title: String,
    date: String,
    location: String,
    icon: Int,
    toEvent: (id: Int) -> Unit
) {
    Box (
        modifier = Modifier
            .width(350.dp)
            .clickable { toEvent(id) }
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
                .background(LightColorScheme.primary),
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
                    color = LightColorScheme.background
                )
                Text (
                    text = date,
                    style = smallButton,
                    fontSize = 17.sp,
                    color = LightColorScheme.background
                )
                Text (
                    text = location,
                    style = smallButton,
                    fontSize = 17.sp,
                    color = LightColorScheme.background
                )
            }
        }
        Box (
            modifier = Modifier
                .width(60.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(LightColorScheme.primaryContainer)
                .align(Alignment.CenterEnd),
            contentAlignment = Alignment.Center
        ) {
            Image (
                painter = painterResource(id = icon),
                contentDescription = "event_icon",
                contentScale = ContentScale.Inside
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
                contentScale = ContentScale.Inside,
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
    }
}

@Composable
fun CommentBanner (
    id: Int,
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
                    .background(LightColorScheme.primary)
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
                        color = LightColorScheme.background
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
                            modifier = Modifier
                        )
                    } else {
                        Text(
                            text = text,
                            style = labelStyle,
                            fontSize = 14.sp,
                            color = LightColorScheme.background
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(LightColorScheme.primaryContainer)
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
                            if (id != 0) {
                                onPublish(value)
                            }
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
    userLocationInput: LatLng?,
    properties: MapProperties,
    uiSettings: MapUiSettings,
    isForPicking: Boolean
) {
    val context = LocalContext.current
    var userLocation by remember { mutableStateOf<LatLng?>(userLocationInput) }
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
                                address = address,
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
                    color = LightColorScheme.primary
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
                    color = LightColorScheme.error
                )
            }
        }
    )
}

