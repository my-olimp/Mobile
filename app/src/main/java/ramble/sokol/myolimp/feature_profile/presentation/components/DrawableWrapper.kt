package ramble.sokol.myolimp.feature_profile.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import ramble.sokol.myolimp.feature_profile.navigation_sheets.SheetNavigation
import ramble.sokol.myolimp.feature_profile.navigation_sheets.SheetRouter

@Composable
fun DrawableWrapper(
    modifier: Modifier = Modifier,
    isRouterBtn: Boolean = false,
    @DrawableRes drawableStart: Int? = null,
    @DrawableRes drawableEnd: Int? = null,
    content: @Composable () -> Unit,
) {
    ConstraintLayout(modifier) {

        val (
            refImgStart,
            refImgEnd,
            refContent
        ) = createRefs()

        Box(Modifier.constrainAs(refContent) {
            start.linkTo(drawableStart?.let {
                refImgStart.end
            } ?: parent.start)

            end.linkTo(drawableEnd?.let {
                refImgEnd.start
            } ?: parent.end)
        }) {
            content()
        }

        drawableStart?.let {
            Image (
                painter = painterResource(id = it),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(refImgStart) {
                        top.linkTo(refContent.top)
                        bottom.linkTo(refContent.bottom)
                        start.linkTo(parent.start)
                    }
                    .clickable {
                        if (isRouterBtn) {
                            SheetRouter.navigateTo(SheetNavigation.Empty())
                        }
                    }
                )
            }

        drawableEnd?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(refImgEnd) {
                        top.linkTo(refContent.top)
                        bottom.linkTo(refContent.bottom)
                        end.linkTo(parent.end)
                    }
                    .clip (
                        CircleShape
                    )
                    .clickable {
                        if (isRouterBtn) {
                            SheetRouter.navigateTo(SheetNavigation.Empty())
                        }
                    }
                )
            }
    }
}
