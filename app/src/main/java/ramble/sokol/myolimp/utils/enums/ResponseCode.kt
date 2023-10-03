@file:Suppress("unused")package ramble.sokol.myolimp.utils.enums

enum class ResponseCode(val code: Int) {
    SUCCESS(200),
    BAD_REQUEST(400),
    FORBIDDEN(403),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    TIMEOUT(408)
    /*TODO add some codes*/
}