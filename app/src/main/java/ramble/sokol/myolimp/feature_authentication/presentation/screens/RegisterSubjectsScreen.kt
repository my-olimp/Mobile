package ramble.sokol.myolimp.feature_authentication.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_authentication.presentation.components.TextHeaderWithCounter
import ramble.sokol.myolimp.feature_authentication.presentation.view_models.RegisterSubjectsViewModel
import ramble.sokol.myolimp.feature_library.presenation.components.SearchTextField
import ramble.sokol.myolimp.ui.theme.BlackRegistrationData
import ramble.sokol.myolimp.ui.theme.BlackRegistrationSubjects
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.SecondaryScreen

@Destination
@Composable
fun RegisterSubjectsScreen (
    navigator: DestinationsNavigator
) {

    val viewModel = getViewModel<RegisterSubjectsViewModel>()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getSubjects()
    })

    OlimpTheme(
        navigationBarColor = SecondaryScreen
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally),
                painter = painterResource(id = R.drawable.auth_my_olimp),
                contentDescription = "image auth my olimp"
            )

            Spacer(modifier = Modifier.padding(top = 16.dp))

            TextHeaderWithCounter(
                headerText = stringResource(id = R.string.subjects),
                counterText = stringResource(id = R.string.three_of_four)
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            SearchTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                onTextChanged = {

                }
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(R.string.choose_necessary_subjects),
                style = TextStyle(
                    fontSize = 11.sp,
                    fontFamily = FontFamily(Font(R.font.regular)),
                    fontWeight = FontWeight(400),
                    color = BlackRegistrationSubjects,
                    letterSpacing = 0.22.sp,
                    textAlign = TextAlign.Start
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.popular_subjects),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.medium)),
                    fontWeight = FontWeight(500),
                    color = BlackRegistrationData,
                    letterSpacing = 0.32.sp,
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

        }
    }
}
