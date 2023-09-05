package ramble.sokol.myolimp.feature_authentication.presentation.screens

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.SecondaryScreen
import ramble.sokol.myolimp.ui.theme.SuccessStatus
import ramble.sokol.myolimp.ui.theme.Transparent
import java.io.File
import java.lang.ref.WeakReference

@Destination()
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
        onEvent = { event ->
            if (event is RegistrationImageEvent.OnImageChanged) {
                selectedImgUri = event.uri
            }
            viewModel.onEvent(event)
        },
        onNext = viewModel::onNext,
        snilsValue = state.snils,
        selectedProfileImg = state.profileImg
    )
}

@Preview
@Composable
fun PrevRegisterImageScreen() {
    RegisterImageScreen({}, {}, "", null)
}

@Composable
fun RegisterImageScreen(
    onEvent: (RegistrationImageEvent) -> Unit,
    onNext: (File) -> Unit,
    snilsValue: String,
    selectedProfileImg: Uri?
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            onEvent(RegistrationImageEvent.OnImageChanged(it))
        }
    )

    val context = LocalContext.current

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
                Spacer(modifier = Modifier.height(24.dp))
                FilledBtn(
                    text = stringResource(id = R.string.further),
                    padding = 0.dp,
                    isEnabled = (selectedProfileImg != null && snilsValue.isNotEmpty())
                ) {
                    try{
                        onNext(uriToFile(selectedProfileImg ?: Uri.EMPTY, context))
                    } catch (e: Exception) {
                        onEvent(RegistrationImageEvent.OnUploadError)
                    }
                }
            }
        }
    }
}

fun uriToFile(uri: Uri, context: Context): File {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        it.moveToFirst()
        val index = it.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA)
        return File(it.getString(index))
    }
    throw Exception("Cursor is null.")
}

