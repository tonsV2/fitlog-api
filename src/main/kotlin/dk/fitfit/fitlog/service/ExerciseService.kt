package dk.fitfit.fitlog.service

import dk.fitfit.fitlog.domain.Exercise
import dk.fitfit.fitlog.domain.Picture
import dk.fitfit.fitlog.domain.Video
import java.time.LocalDateTime

interface ExerciseService : CrudService<Exercise, Long> {
    fun findAllAfter(updatedDateTime: LocalDateTime): Iterable<Exercise>
    fun update(id: Long, exercise: Exercise): Exercise
    fun addPicture(exerciseId: Long, pictureId: Long): Picture
    fun removePicture(exerciseId: Long, pictureId: Long): Boolean
    fun addVideo(exerciseId: Long, videoId: Long): Video
    fun removeVideo(exerciseId: Long, videoId: Long): Boolean
}
