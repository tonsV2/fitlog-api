package dk.fitfit.fitlog.domain.dto

data class ExerciseRequest(
        val name: String,
        val description: String,
        val id: Long?,
        val videos: List<VideoRequest>?,
        val pictures: List<PictureRequest>?
)
