package ramble.sokol.myolimp.feature_profile.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_profile.navigation_sheets.SheetNavigation
import ramble.sokol.myolimp.feature_profile.navigation_sheets.SheetRouter
import ramble.sokol.myolimp.ui.theme.SheetTitle

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetLayout(
    sheetState: ModalBottomSheetState,
    name: String,
    isCenter: Boolean = true,
    onDetach: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(
            topStart = 40.dp,
            topEnd = 40.dp
        ),
        sheetContent = {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 18.dp, horizontal = 32.dp)
            ) {
                if (isCenter) {
                    DrawableWrapper(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 32.dp),
                        drawableEnd = R.drawable.ic_profile_cancel,
                        isRouterBtn = true
                    ) {
                        Text(
                            text = name,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.medium)),
                                fontWeight = FontWeight(500),
                                color = SheetTitle,
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.4.sp,
                            )
                        )
                    }
                } else {

                    Row (
                        modifier = Modifier
                           .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = name,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.medium)),
                                fontWeight = FontWeight(500),
                                color = SheetTitle,
                                textAlign = TextAlign.Start,
                                letterSpacing = 0.4.sp,
                            )
                        )

                        Image (
                            modifier = Modifier
                                .clip(
                                    CircleShape
                                )
                                .clickable {
                                    SheetRouter.navigateTo(SheetNavigation.Empty(onDetach = onDetach))
                                },
                            painter = painterResource(id = R.drawable.ic_profile_cancel),
                            contentDescription = "close sheet"
                        )
                    }
                }

                content()
            }
        },
    ) {}
}
