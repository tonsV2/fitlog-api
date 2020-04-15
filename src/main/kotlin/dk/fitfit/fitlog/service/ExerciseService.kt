package dk.fitfit.fitlog.service

import dk.fitfit.fitlog.domain.Exercise
import java.time.LocalDateTime

interface ExerciseService : CrudService<Exercise, Long> {
    fun findAllAfter(updatedDateTime: LocalDateTime): Iterable<Exercise>
    fun update(id: Long, exercise: Exercise): Exercise
    fun addPicture(exerciseId: Long, pictureId: Long)
    fun removePicture(exerciseId: Long, pictureId: Long): Boolean
    fun addVideo(exerciseId: Long, videoId: Long)
    fun removeVideo(exerciseId: Long, videoId: Long): Boolean
}
