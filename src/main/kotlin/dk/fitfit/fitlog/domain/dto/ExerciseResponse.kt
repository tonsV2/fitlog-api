package dk.fitfit.fitlog.domain.dto

data class ExerciseResponse(
        val name: String,
        val description: String,
        val videos: List<VideoResponse> = mutableListOf(),
        val pictures: List<PictureResponse> = mutableListOf(),
        val id: Long = 0
)
