package stuba.fiit.sk.eventsphere.ui.activities.welcome

import android.content.res.Configuration
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.ui.components.ButtonComponent
import stuba.fiit.sk.eventsphere.ui.theme.LightColorScheme
import stuba.fiit.sk.eventsphere.ui.theme.labelStyle
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle
import java.util.Locale


@Composable
fun WelcomeScreen (
    toLogin: () -> Unit,
    toRegister: () -> Unit,
    toHome: () -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WelcomeTopBar()

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
                fillColor = LightColorScheme.primary,
                textColor = LightColorScheme.background,
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
                fillColor = LightColorScheme.background,
                textColor = LightColorScheme.primary,
                text = stringResource(id = R.string.register_button),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )
            GuestButton(toHome)
        }
    }
}

@Composable
fun WelcomeTopBar (

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
                .padding(40.dp),
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
                maxLines = 2,
                lineHeight = 35.sp,
                letterSpacing = 2.sp,
            )
        }
    }
}


@Composable
fun GuestButton (
    toHome: () -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(
            onClick = toHome
        ) {
            Text(
                text = stringResource(id = R.string.guest_continue),
                style = labelStyle,
                textAlign = TextAlign.Center,
                color = LightColorScheme.onBackground
            )

            Spacer(modifier = Modifier.padding(3.dp))

            Text(
                text = stringResource(id = R.string.guest),
                style = labelStyle,
                textAlign = TextAlign.Center,
                color = LightColorScheme.primary
            )
        }
    }
}