package ramble.sokol.myolimp.feature_library.presenation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_authentication.presentation.screens.forgot_password.VerticalSpacer
import ramble.sokol.myolimp.feature_library.presenation.components.library.LibraryItem
import ramble.sokol.myolimp.feature_profile.presentation.components.HorizontalSpacer
import ramble.sokol.myolimp.ui.theme.BlackProfile
import ramble.sokol.myolimp.ui.theme.BlueStart
import ramble.sokol.myolimp.ui.theme.BottomBarTheme
import ramble.sokol.myolimp.ui.theme.GreyProfileData
import ramble.sokol.myolimp.ui.theme.SheetTitle
import ramble.sokol.myolimp.ui.theme.White
import ramble.sokol.myolimp.ui.theme.mediumType
import ramble.sokol.myolimp.ui.theme.regularType


@[Destination Composable]
fun OlympiadScreen(
    navController: NavController
) {
    BottomBarTheme(
        navController = navController,
        onReload = { /*TODO fuck*/}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 52.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_connection_error),
                contentDescription = "connection error",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .padding(horizontal = 54.dp, vertical = 12.dp)
            )
            Text(
                text = "Высшая проба" /* olympiad name */,
                style = mediumType(
                    color = SheetTitle,
                    fontSize = 25.sp,
                    lineHeight = 22.sp
                )
            )
            Text(
                text = "Всероссийская предметная олимпиада школьников, проводимая Высшей школой экономики на базе самого университета, других вузов и школ России и ближнего зарубежья. В 2019-2020 учебных годах олимпиада проводится по 25 профилям.",
                style = regularType(
                    color = SheetTitle,
                    letterSpacing = 0.26.sp,
                    fontSize = 13.sp
                ),
                modifier = Modifier.padding(vertical = 12.dp)
            )
            OlympiadSection(header = "Организаторы") {
                LazyRow(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    items(5) {
                        Column {
                            Image(
                                painter = painterResource(id = R.drawable.ic_main_no_plans),
                                contentDescription = "s$it",

                            )
                            VerticalSpacer(height = 4.dp)
                            Text(text = "text $it")
                        }
                    }
                }
            }
            OlympiadSection(header = "Расписание и этапы") {
                LazyColumn(
                    modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(36.dp)
                ) {
                    items(4) {

                        OlympiadStage(header = "Header $it", date = "date $it-${it.plus(4)}-$it")
                    }
                    item { OlympiadStage(header = "last", date = "last", isLastStage = true) }
                }
            }
            OlympiadSection(header = "Дисциплины") {
                LazyRow(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(5) {
                        LibraryItem(
                            title = "item $it"
                        ) {

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OlympiadStage(
    header: String,
    date: String,
    isLastStage: Boolean = false
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Column(
            modifier = Modifier.fillMaxWidth(0.1f),
            verticalArrangement = Arrangement.Center
        ) {
            Canvas(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
            ) {
                val radius = this.size.width / 2f
                if(!isLastStage)drawLine(
                    color = BlackProfile,
                    start = Offset(x = radius, y = radius),
                    end = Offset(x = radius, y = radius + 36.dp.value + (radius * 2f)),
                    strokeWidth = 5f
                )
                drawCircle(color = BlueStart,radius = radius)
            }
        }
        HorizontalSpacer(width = 10.dp)
        Column {
            Text(
                text = header,
                style = mediumType(
                    color = SheetTitle,
                    fontSize = 14.sp
                )
            )
            Text(
                text = date,
                style = regularType(
                    color = GreyProfileData,
                    fontSize = 12.sp
                )
            )
        }
    }
}



@Composable
fun OlympiadSection(
    header: String,
    content: @Composable () -> Unit
) {
    Column (
        modifier = Modifier.padding(vertical = 12.dp)
    ){
        Text(
            text = header,
            style = mediumType(fontSize = 20.sp)
        )
        VerticalSpacer(height = 12.dp)
        Column(
            modifier = Modifier
                .background(White, RoundedCornerShape(25.dp))
                .fillMaxWidth()
        ) {
            content.invoke()
        }
    }
}
