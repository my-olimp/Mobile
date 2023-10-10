package ramble.sokol.myolimp.feature_profile.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.ui.theme.mediumType

@Composable
fun CheckList(
    labels: List<String> = emptyList(),
    options: List<String> = emptyList()
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items(labels) { label ->
            Text(
                text = label,
                style = mediumType(
                    fontSize = 20.sp,
                    letterSpacing = 0.4.sp,
                )
            )
        }
    }
}