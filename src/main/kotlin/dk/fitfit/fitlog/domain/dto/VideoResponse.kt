package dk.fitfit.fitlog.domain.dto

data class VideoResponse(
        val url: String,
        val creator: UserResponse,
        val id: Long
)
