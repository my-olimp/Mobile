package ramble.sokol.myolimp.feature_authentication.presentation.screens

import android.graphics.BitmapFactory
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_authentication.presentation.components.TextHeaderWithCounter
import ramble.sokol.myolimp.feature_authentication.presentation.view_models.RegisterImageViewModel
import ramble.sokol.myolimp.feature_authentication.presentation.view_models.RegistrationImageEvent
import ramble.sokol.myolimp.feature_profile.presentation.components.OutlinedText
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.SecondaryScreen
import ramble.sokol.myolimp.ui.theme.Transparent
import java.io.File

@Destination
@Composable
internal fun RegisterImageScreen(
    navigator: DestinationsNavigator,
    isWorkScreen: Boolean = false
) {
    val viewModel = getViewModel<RegisterImageViewModel>()
    val state by viewModel.state.collectAsState()
    var selectedImgUri by remember {
        mutableStateOf(state.profileImg)
    }

    RegisterImageScreen(
        onEvent = { event ->
            if (event is RegistrationImageEvent.OnImageChanged) {
                selectedImgUri = event.uri
            }
            viewModel.onEvent(event)
        },
        navigator = navigator,
        snilsValue = state.snils,
        selectedProfileImg = state.profileImg,
        isHiddenSnils = isWorkScreen
    )
}

@Composable
fun RegisterImageScreen(
    onEvent: (RegistrationImageEvent) -> Unit,
    snilsValue: String,
    navigator: DestinationsNavigator,
    selectedProfileImg: Uri?,
    isHiddenSnils: Boolean = false
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            onEvent(RegistrationImageEvent.OnImageChanged(it))
        }
    )

    val context = LocalContext.current

    OlimpTheme(
        navigationBarColor = SecondaryScreen,
        onReload = {},
        content = {
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
                        TextHeaderWithCounter(
                            headerText = stringResource(R.string.register_image_screen_title),
                            counterText = stringResource(R.string.four_of_four)
                        )
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    IconButton(
                        onClick = {
                            launcher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                        modifier = Modifier.size(150.dp)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .size(150.dp)
                                .align(Alignment.CenterHorizontally)
                                .clip(CircleShape),
                            model = selectedProfileImg ?: R.drawable.ic_default_img,
                            contentDescription = "user logo",
                            contentScale = ContentScale.Crop
                        )
                    }

                    if(!isHiddenSnils) {
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedText(
                            previousData = snilsValue,
                            label = stringResource(R.string.label_snils),
                            isEnabled = true,
                            onTextChanged = {
                                onEvent(RegistrationImageEvent.OnSnilsChanged(it))
                            },
                            isError = false
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    FilledBtn(
                        text = stringResource(id = R.string.further),
                        padding = 0.dp,
                        isEnabled = (selectedProfileImg != null && (isHiddenSnils || snilsValue.isNotEmpty()))
                    ) {
                        try {
                            val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(selectedProfileImg ?: Uri.EMPTY))
                            val pngFile = File(context.cacheDir, "converted_image.png")
                            if (pngFile.exists()) pngFile.delete()
                            pngFile.createNewFile()
                            onEvent(RegistrationImageEvent.OnSubmit(pngFile, bitmap, navigator))
                        } catch (e: Exception) {
                            onEvent(RegistrationImageEvent.OnUploadError)
                        }
                    }
                }
            }
        }
    )
}
