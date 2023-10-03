package ramble.sokol.myolimp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_authentication.presentation.screens.forgot_password.VerticalSpacer
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn
import ramble.sokol.myolimp.ui.theme.GreySecondary
import ramble.sokol.myolimp.ui.theme.LightBlack
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.SecondaryScreen
import ramble.sokol.myolimp.ui.theme.mediumType

@[Composable Preview(showBackground = true)]
fun ConnectionErrorScreen() {
    OlimpTheme(
        navigationBarColor = SecondaryScreen
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 33.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_connection_error),
                contentDescription = "connection error",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .padding(horizontal = 39.dp)
            )

            VerticalSpacer(height = 24.dp)

            Text(
                text = stringResource(id = R.string.something_wrong),
                style = TextStyle(
                    fontSize = 25.sp,
                    lineHeight = 24.5.sp,
                    fontFamily = FontFamily(Font(R.font.bold)),
                    fontWeight = FontWeight(700),
                    color = LightBlack,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.5.sp,
                )
            )

            VerticalSpacer(height = 8.dp)

            Text(
                text = stringResource(id = R.string.check_your_connection),
                style = mediumType(
                    fontSize = 14.sp,
                    lineHeight = 13.72.sp,
                    color = GreySecondary,
                    letterSpacing = 0.28.sp
                ),
                textAlign = TextAlign.Center
            )
            
            VerticalSpacer(height = 24.dp)

            FilledBtn(
                text = stringResource(id = R.string.reload),
                padding = 0.dp
            ) {
                /*TODO*/
            }

        }
    }
}