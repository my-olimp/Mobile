package ramble.sokol.myolimp.feature_library.domain.states

data class TaskState(
    var answer: String = "",
    var isError: Boolean = false,
    var isSuccess: Boolean = false
)
