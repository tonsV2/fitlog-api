package dk.fitfit.fitlog.domain.dto

data class ExerciseResponse(
        val name: String,
        val description: String,
        val creator: UserResponse,
        val videos: List<VideoResponse>?,
        val pictures: List<PictureResponse>?,
        val id: Long = 0
)
