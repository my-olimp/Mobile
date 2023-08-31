package ramble.sokol.myolimp.feature_authentication.domain.utils

fun String.onlyLetters(): Boolean = (firstOrNull { !it.isLetter() } == null)

fun String.onlyNumbers(): Boolean = (firstOrNull { !it.isDigit() } == null)
