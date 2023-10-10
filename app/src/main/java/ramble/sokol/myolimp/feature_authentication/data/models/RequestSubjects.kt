package ramble.sokol.myolimp.feature_authentication.data.models

import com.google.gson.annotations.SerializedName
import javax.security.auth.Subject

data class RequestSubjects (
    @SerializedName("subjects") val subjects: List<String>
)
