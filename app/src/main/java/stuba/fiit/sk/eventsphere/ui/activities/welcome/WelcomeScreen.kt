package stuba.fiit.sk.eventsphere.ui.activities.welcome

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.ui.components.PrimaryButton
import stuba.fiit.sk.eventsphere.ui.components.SecondaryButton
import stuba.fiit.sk.eventsphere.ui.theme.labelStyle
import stuba.fiit.sk.eventsphere.ui.theme.welcomeStyle

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
                    text = " Unlock the Gateway to Unforgettable Moments!",
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
        
        Spacer(
            modifier = Modifier
                .height(50.dp)
        )

        PrimaryButton(text = "Login", onClick = toLogin)

        Spacer(
            modifier = Modifier
                .height(25.dp)
        )

        SecondaryButton(text = "Register", onClick = toRegister)

        Spacer(
            modifier = Modifier
                .height(25.dp)
        )

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
                    text = "Continue as",
                    style = labelStyle,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.padding(3.dp))

                Text(
                    text = "guest",
                    style = labelStyle,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}