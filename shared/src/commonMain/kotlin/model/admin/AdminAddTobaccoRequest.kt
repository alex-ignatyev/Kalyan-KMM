package model.admin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdminAddTobaccoRequest(
    @SerialName("isManual") val isManual: Boolean,
    @SerialName("taste") val taste: String,
    @SerialName("company") val company: String,
    @SerialName("line") val line: String,
    @SerialName("strength") val strength: Long
)
