package ramble.sokol.myolimp.feature_main.presenation.screens

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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
import ramble.sokol.myolimp.ui.theme.DisabledBlue
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
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 60.dp)
        ) {
            item {
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
            }
            item {
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
            }
            item {
                OlympiadHeader(header = "Расписание и этапы")

                OlympiadStage(
                    header = "first",
                    date = "first",
                    modifier = Modifier
                        .background(White, RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                        .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 36.dp)
                        .fillMaxWidth()
                )
            }
            items(3) {

                OlympiadStage(
                    header = "Header $it",
                    date = "date $it-${it.plus(4)}-$it",
                    modifier = Modifier
                        .background(White)
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 36.dp)
                )
            }
            item {
                OlympiadStage(
                    header = "last", date = "last", isLastStage = true, modifier = Modifier
                        .background(
                            White,
                            RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp)
                        )
                        .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                )
                VerticalSpacer(height = 12.dp)
            }
            item {
                OlympiadSection(header = "Дисциплины") {
                    LazyRow(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(5) {
                            LibraryItem(
                                title = "item $it",
                                subject = "математика"
                            ) {
                                /*TODO click*/
                            }
                        }
                    }
                }
            }
            item {
                OlympiadHeader(header = "Льготы")
            }
            items(5) {
                when (it) {
                    0 -> {
                        OlympiadBenefits(
                            benefit = "first benefit",
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    White,
                                    RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
                                )
                                .padding(top = 24.dp, bottom = 14.dp, start = 16.dp, end = 40.dp)
                        )
                    }
                    4 -> {
                        OlympiadBenefits(
                            benefit = "last benefit",
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(White)
                                .padding(bottom = 16.dp, start = 16.dp)
                        )
                    }
                    else -> OlympiadBenefits(
                        benefit = "long fucking benefit das ist deutsch und ich heiße oleg aber ich komme aus russland $it",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(White)
                            .padding(start = 16.dp, end = 40.dp, bottom = 14.dp)
                    )
                }
            }
            item {
                Text(
                    text = "Important! remark on benefits sie schreiben über das haus und die frau Lindemann ",
                    style = regularType(
                        fontSize = 10.sp,
                        color = GreyProfileData
                    ),
                    modifier = Modifier
                        .background(
                            White,
                            RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp)
                        )
                        .padding(16.dp)
                )
                VerticalSpacer(height = 24.dp)
            }
            item { 
                RoundedButton(
                    text = stringResource(id = R.string.will_participate),
                    modifier = Modifier.padding(start = 36.dp,end = 36.dp, bottom = 20.dp),
                    round = 15.dp
                ) {

                }
            }
        }
    }
}

@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    text: String = "",
    round: Dp = 36.dp,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(round),
        colors = ButtonDefaults.buttonColors(
            containerColor = BlueStart,
            disabledContainerColor = DisabledBlue
        ),
        enabled = isEnabled
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.medium)),
                fontWeight = FontWeight(600),
                color = White,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
    }
}

@Composable
fun OlympiadStage(
    modifier: Modifier = Modifier,
    header: String,
    date: String,
    isLastStage: Boolean = false
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
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
private fun OlympiadBenefits(
    benefit: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ){
        Column(
            modifier = Modifier.fillMaxWidth(0.05f),
            verticalArrangement = Arrangement.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(color = BlueStart, radius = this.size.width / 2f)
            }
        }
        HorizontalSpacer(width = 8.dp)

        Text(
            text = benefit,
            style = mediumType(
                color = SheetTitle,
                fontSize = 12.sp
            )
        )
    }
}

@Composable
private fun OlympiadSection(
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

@Composable
private fun OlympiadHeader(header: String) {
    Text(
        text = header,
        style = mediumType(fontSize = 20.sp),
        modifier = Modifier.padding(top = 12.dp)
    )
    VerticalSpacer(height = 12.dp)
}
