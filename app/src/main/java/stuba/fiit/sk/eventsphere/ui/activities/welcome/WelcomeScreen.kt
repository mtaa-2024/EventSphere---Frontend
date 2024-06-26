package stuba.fiit.sk.eventsphere.ui.activities.welcome
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.ui.components.ButtonComponent
import stuba.fiit.sk.eventsphere.ui.isInternetAvailable
import stuba.fiit.sk.eventsphere.ui.theme.labelStyle
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle

@Composable
fun WelcomeScreen(
    toLogin: () -> Unit,
    toRegister: () -> Unit,
    toHome: () -> Unit
) {
    if(determineLayout()) {
        TabletWelcomeScreen(toLogin, toRegister, toHome)
    }else{
        MobileWelcomeScreen(toLogin, toRegister, toHome)
    }
}


@Composable
fun MobileWelcomeScreen (
    toLogin: () -> Unit,
    toRegister: () -> Unit,
    toHome: () -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            Image (
                painter = painterResource(id = R.drawable.welcome_background),
                contentDescription = "welcome_background",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Column (
                modifier = Modifier
                    .padding(35.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .width(350.dp)
                )
                Spacer(
                    modifier = Modifier
                        .height(35.dp)
                )
                Text(
                    text = stringResource(id = R.string.welcome_text),
                    style = welcomeStyle,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = Color.White,
                    maxLines = 3,
                    lineHeight = 35.sp,
                    letterSpacing = 1.sp,
                )
            }
        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp, 0.dp),
        ) {
            Spacer(
                modifier = Modifier
                    .height(50.dp)
            )

            ButtonComponent (
                onClick = toLogin,
                fillColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.background,
                text = stringResource(id = R.string.login_button),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )

            ButtonComponent (
                onClick = toRegister,
                fillColor = MaterialTheme.colorScheme.background,
                textColor = MaterialTheme.colorScheme.primary,
                text = stringResource(id = R.string.register_button),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val context = LocalContext.current
                val string = stringResource(id = R.string.no_internet_connection)
                TextButton(
                    onClick = {
                        if(isInternetAvailable(context)) {
                            toHome()
                        }else{
                            Toast.makeText(context, string, Toast.LENGTH_SHORT).show();

                        }
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.guest_continue),
                        style = labelStyle,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.padding(3.dp))

                    Text(
                        text = stringResource(id = R.string.guest),
                        style = labelStyle,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun TabletWelcomeScreen (
    toLogin: () -> Unit,
    toRegister: () -> Unit,
    toHome: () -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Image (
                painter = painterResource(id = R.drawable.welcome_background),
                contentDescription = "welcome_background",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Column (
                modifier = Modifier
                    .padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .width(100.dp)
                )
                Spacer(
                    modifier = Modifier
                        .height(35.dp)
                )
                Text(
                    text = stringResource(id = R.string.welcome_text),
                    style = welcomeStyle,
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    color = Color.White,
                    maxLines = 2,
                    lineHeight = 40.sp,
                    letterSpacing = 2.sp,
                )
            }
        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .height(100.dp)
            )

            ButtonComponent (
                onClick = toLogin,
                fillColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.background,
                text = stringResource(id = R.string.login_button),
                modifier = Modifier
                    .width(800.dp)
                    .height(60.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            ButtonComponent (
                onClick = toRegister,
                fillColor = MaterialTheme.colorScheme.background,
                textColor = MaterialTheme.colorScheme.primary,
                text = stringResource(id = R.string.register_button),
                modifier = Modifier
                    .width(800.dp)
                    .height(60.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(50.dp)
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val context = LocalContext.current
                val string = stringResource(id = R.string.no_internet_connection)
                TextButton(
                    onClick = {
                        if(isInternetAvailable(context)) {
                            toHome()
                        } else {
                            Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
                        }
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.guest_continue),
                        style = labelStyle,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.padding(3.dp))

                    Text(
                        text = stringResource(id = R.string.guest),
                        style = labelStyle,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}


@Composable
fun determineLayout(): Boolean {
    val context = LocalContext.current
    val screenWidthPx = context.resources.displayMetrics.widthPixels
    val breakpoint = 1200.dp
    return screenWidthPx.dp >= breakpoint
}