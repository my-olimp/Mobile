package ramble.sokol.myolimp.feature_library.presenation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.GreyProfileData
import ramble.sokol.myolimp.ui.theme.MainBackground
import ramble.sokol.myolimp.ui.theme.White
import ramble.sokol.myolimp.ui.theme.mediumType
import ramble.sokol.myolimp.ui.theme.regularType

//@Preview
//@Composable
//fun PrevSearchTextField() {
//    SearchTextField(Modifier, {}, {})
//}

@Composable
fun SearchTextField(
    modifier: Modifier,
    onTextChanged: (String) -> Unit,
    onCancelSearching: () -> Unit,
) {
    val textValue = remember { mutableStateOf("") }
    var hasFocus by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged {
                hasFocus = it.isFocused
            },
        shape = RoundedCornerShape(12.dp),
        label = {
            Row {
                Text(
                    text = stringResource(R.string.search),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.regular)),
                        fontWeight = FontWeight(400),
                        color = if (hasFocus) BlueStart else GreyProfileData,
                        letterSpacing = 0.3.sp,
                    )
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = BlueStart,
            focusedLabelColor = BlueStart,
            cursorColor = BlueStart,
            backgroundColor = White,

            ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextChanged(it)
        },
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .size(20.dp),
                tint = if (hasFocus) BlueStart else GreyProfileData,
                painter = painterResource(id = R.drawable.ic_calendar_search),
                contentDescription = "search"
            )
        },
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .clickable {
                        textValue.value = ""
                        focusManager.clearFocus()
                        onCancelSearching()
                    },
                tint = if (hasFocus) BlueStart else GreyProfileData,
                painter = painterResource(id = R.drawable.ic_profile_cancel),
                contentDescription = "search"
            )
        }
    )
}

@Composable
fun FilterIcon(
    onClick: () -> Unit = {},
    isActive: Boolean = false
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.background(
            color = if (isActive) BlueStart else White,
            shape = RoundedCornerShape(5.dp)
        )
    ) {
        Icon(
            painterResource(id = R.drawable.ic_library_sorting),
            contentDescription = "Filter articles",
            tint = if (isActive) White else GreyProfileData
        )
    }
}

@Composable
fun FavoriteIcon(
    onClick: () -> Unit = {},
    isActive: Boolean = false
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.background(
            color = if (isActive) BlueStart else White,
            shape = RoundedCornerShape(5.dp)
        )
    ) {
        Icon(
            painterResource(id = R.drawable.ic_article_favorite),
            contentDescription = "Filter articles",
            tint = if (isActive) White else GreyProfileData
        )
    }
}


@Composable
fun LibraryBox(
    title: String
) {
    Column(
        modifier = Modifier
            .padding(top = 12.dp, bottom = 18.dp, start = 12.dp, end = 12.dp)
            .fillMaxWidth()
            .background(
                color = White,
                shape = RoundedCornerShape(25.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = title,
                style = regularType(
                    color = BlackProfile,
                    fontSize = 18.sp
                )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

    }
}

@Composable
fun LibraryItem(
    backgroundPainter: Painter = painterResource(id = R.drawable.ic_main_background_blue),
    type: String = "",
    subject: String = "",
    title: String = "",
) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .paint(
                painter = backgroundPainter,
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(start = 8.dp, top = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            if (type != "") {
                LibrarySubtitleText(text = type)
            }
            if (subject != "") {
                Spacer(modifier = Modifier.height(4.dp))
                LibrarySubtitleText(text = subject)
            }
        }
        if (title != "") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .align(Alignment.BottomStart),
            ) {
                Text(
                    text = title,
                    style = mediumType(
                        color = White,
                        fontSize = 10.sp
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun LibrarySubtitleText(
    text: String
) {
    Text(
        text = text,
        modifier = Modifier
            .background(
                color = MainBackground,
                shape = RoundedCornerShape(size = 4.dp)
            )
            .padding(horizontal = 2.dp, vertical = 1.dp),
        style = regularType(
            color = White,
            fontSize = 8.sp
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}