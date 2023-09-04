package ramble.sokol.myolimp.feature_authentication.presentation.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.SecondaryScreen
import ramble.sokol.myolimp.ui.theme.Transparent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.feature_authentication.presentation.view_models.RegisterImageViewModel
import ramble.sokol.myolimp.feature_authentication.presentation.view_models.RegistrationImageEvent
import ramble.sokol.myolimp.feature_profile.presentation.components.OutlinedText
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.SuccessStatus

@Composable
internal fun RegisterImageScreen(
    navigator: DestinationsNavigator
) {
    val viewModel = getViewModel<RegisterImageViewModel>()
    val state by viewModel.state.collectAsState()
    var selectedImgUri by remember {
        mutableStateOf(state.profileImg)
    }
    RegisterImageScreen(
        onEvent = {event ->
            if (event is RegistrationImageEvent.OnImageChanged) {
                selectedImgUri = event.uri
            }
            viewModel.onEvent(event)
        },
        snilsValue = state.snils,
        selectedProfileImg = state.profileImg
    )
}

@Preview
@Composable
fun PrevRegisterImageScreen() {
    RegisterImageScreen({}, "", null)
}

@Composable
fun RegisterImageScreen(
    onEvent: (RegistrationImageEvent) -> Unit,
    snilsValue: String,
    selectedProfileImg: Uri?
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            onEvent(RegistrationImageEvent.OnImageChanged(it))
        }
    )

    OlimpTheme(
        navigationBarColor = SecondaryScreen
    ) {
        Column(
            modifier = Modifier
                .background(Transparent)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.auth_my_olimp),
                    contentDescription = "image auth my olimp"
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Фото и документ",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.medium)),
                            fontWeight = FontWeight(500),
                            color = BlackProfile,
                            letterSpacing = 0.4.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    Text(
                        text = "4 из 4",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.medium)),
                            fontWeight = FontWeight(500),
                            color = SuccessStatus,
                            letterSpacing = 0.4.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                IconButton(
                    onClick = {
                        launcher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                    modifier = Modifier.size(200.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(200.dp)
                            .align(Alignment.CenterHorizontally)
                            .clip(CircleShape),
                        model = selectedProfileImg ?: R.drawable.ic_default_img,
                        contentDescription = "user logo",
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedText(
                    previousData = snilsValue,
                    label = "СНИЛС",
                    isEnabled = true,
                    onTextChanged = {
                        onEvent(RegistrationImageEvent.OnSnilsChanged(it))
                    },
                    isError = false
                )
                Spacer(modifier = Modifier.height(16.dp))
                FilledBtn(
                    text = stringResource(id = R.string.further),
                    padding = 0.dp,
                    isEnabled = (selectedProfileImg != null) //
                ) {

                }
            }
        }
    }
}