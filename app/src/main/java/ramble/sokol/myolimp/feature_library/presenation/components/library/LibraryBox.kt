package ramble.sokol.myolimp.feature_library.presenation.components.library

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.MainPageBlue
import ramble.sokol.myolimp.ui.theme.White
import ramble.sokol.myolimp.ui.theme.regularType

@Composable
fun LibraryBox(
    title: String,
    isLearnMore: Boolean = true,
    content: @Composable () -> Unit = { Text("") }
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = White,
                shape = RoundedCornerShape(25.dp)
            )
            .padding(top = 12.dp, bottom = 18.dp, start = 12.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = regularType(
                    color = BlackProfile,
                    fontSize = 18.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { }
            ) {
                if (isLearnMore) {
                    Text(
                        text = stringResource(R.string.learn_more),
                        style = regularType(
                            color = MainPageBlue,
                            fontSize = 12.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = stringResource(R.string.learn_more),
                        modifier = Modifier.size(16.dp),
                        tint = MainPageBlue,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}
