package ramble.sokol.myolimp.feature_library.presenation.components.article

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.ui.theme.LibraryItemDisabled
import ramble.sokol.myolimp.ui.theme.LibraryItemSelected
import ramble.sokol.myolimp.ui.theme.White

@Composable
fun PartItem(
    itemId: Int,
    selected: Boolean = false,
    partType: String = "t",
    onClick: (Int) -> Unit
) {

    val id by remember {
        mutableIntStateOf(itemId)
    }

    Column(
        modifier = Modifier
            .fillMaxHeight(0.06f)
            .background(
                color = if (selected) LibraryItemSelected else LibraryItemDisabled,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(all = 5.dp)
            .clickable {
                onClick(id)
            }
    ) {
        Icon(
            painter = painterResource(id = if (partType == "t") R.drawable.ic_library_read_item else R.drawable.ic_library_exam_item),
            contentDescription = "read item",
            modifier = Modifier
                .padding(all = 4.dp)
                .fillMaxSize()
            ,
            tint = White
        )
    }
}