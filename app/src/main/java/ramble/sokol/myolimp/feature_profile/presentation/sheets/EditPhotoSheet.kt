package ramble.sokol.myolimp.feature_profile.presentation.sheets

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_profile.presentation.components.DrawableWrapper
import ramble.sokol.myolimp.feature_profile.presentation.components.ProfileFilledBtn
import ramble.sokol.myolimp.feature_profile.presentation.components.ProfileOutlinedBtn
import ramble.sokol.myolimp.feature_profile.presentation.view_models.ProfileViewModel
import ramble.sokol.myolimp.feature_profile.utils.ProfileEvent
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.White

@Composable
fun EditPhotoSheet(
    viewModel: ProfileViewModel
) {

    val state = viewModel.state.collectAsState()
    val imgState = viewModel.imgState.collectAsState()

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current.applicationContext)
            .data("https://storage.yandexcloud.net/myolimp/user/avatar/${state.value.id}.webp")
            .crossfade(true)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .diskCachePolicy(CachePolicy.DISABLED)
            .build()
    )

    LaunchedEffect(key1 = imgState.value.isImgChanged) {
        painter.onForgotten()
        painter.onRemembered()

        viewModel.onEvent(ProfileEvent.OnImgUpdated)
    }

    var selectedImgUri by remember {
        mutableStateOf(viewModel.imgState.value.profileImg)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri ->
            selectedImgUri = uri
            viewModel.onEvent(ProfileEvent.OnImgChanged(uri))
        }
    )

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(top = 32.dp)
    ) {

        Image(
            modifier = Modifier
                .size(175.dp)
                .align(CenterHorizontally)
                .clip(CircleShape),
            painter = if (selectedImgUri != null) rememberAsyncImagePainter(selectedImgUri) else painter,
            contentDescription = "user logo",
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 52.dp)
                .background(
                    color = White,
                    shape = RoundedCornerShape(size = 16.dp)
                )
                .border(
                    width = 1.dp,
                    color = BlueStart,
                    shape = RoundedCornerShape(size = 16.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = White
            ),
            onClick = {
                // to open window with choosing image
                launcher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
        ) {

            DrawableWrapper (
                modifier = Modifier
                    .fillMaxWidth(),
                drawableStart = R.drawable.ic_profile_folder
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(R.string.choose_photo),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.medium)),
                        fontWeight = FontWeight(500),
                        color = BlueStart,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.4.sp,
                    )
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProfileFilledBtn(
                text = stringResource(R.string.save),
                enabled = (selectedImgUri.toString().startsWith("content://"))
            ) {
                selectedImgUri = null
                viewModel.onEvent(ProfileEvent.OnImgSave)
            }

            ProfileOutlinedBtn(text = stringResource(R.string.delete)) {
                selectedImgUri = null
                viewModel.onEvent(ProfileEvent.OnDeleteImg)
            }
        }
    }
}
