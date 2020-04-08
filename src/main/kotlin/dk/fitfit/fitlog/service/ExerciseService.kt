package dk.fitfit.fitlog.service

import dk.fitfit.fitlog.domain.Exercise
import dk.fitfit.fitlog.domain.Picture
import dk.fitfit.fitlog.domain.Video

interface ExerciseService : CrudService<Exercise, Long> {
    fun getPicture(id: Long): Picture
    fun save(exerciseId: Long, picture: Picture): Picture
    fun deletePicture(exerciseId: Long, pictureId: Long)
    fun getVideo(id: Long): Video
    fun save(exerciseId: Long, video: Video): Video
    fun deleteVideo(exerciseId: Long, videoId: Long)
}
