package dk.fitfit.fitlog.service.impl

import dk.fitfit.fitlog.domain.Exercise
import dk.fitfit.fitlog.domain.Picture
import dk.fitfit.fitlog.domain.Video
import dk.fitfit.fitlog.repository.ExerciseRepository
import dk.fitfit.fitlog.repository.core.UpdatedAfterRepository
import dk.fitfit.fitlog.service.ExerciseService
import dk.fitfit.fitlog.service.PictureService
import dk.fitfit.fitlog.service.VideoService
import dk.fitfit.fitlog.util.merge
import java.time.LocalDateTime
import javax.inject.Named
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Singleton
@Transactional
class ExerciseServiceDefault(
        @Named("ExerciseRepository") override val updatedAfterRepository: UpdatedAfterRepository<Exercise, LocalDateTime>,
//        override val updatedAfterRepository: UpdatedAfterRepository<Exercise, LocalDateTime>,
//        override val updatedAfterRepository: ExerciseRepository,
        override val crudRepository: ExerciseRepository,
        private val videoService: VideoService,
        private val pictureService: PictureService,
        private val entityManager: EntityManager
) : ExerciseService {

// TODO: Should be moved to CrudService
// TODO: Should not use entityManager
    override fun update(id: Long, exercise: Exercise): Exercise {
        val existing = get(id)
        val updated = existing.merge(exercise)
        return entityManager.merge(updated)
//        return repository.save(updated)
    }

    override fun addPicture(exerciseId: Long, pictureId: Long): Picture {
        val exercise = get(exerciseId)
        val picture = pictureService.get(pictureId)
        val pictures = exercise.pictures
        if (pictures == null) {
            exercise.pictures = mutableListOf(picture)
        } else {
            pictures.add(picture)
        }
        save(exercise)
        return picture
    }

    override fun removePicture(exerciseId: Long, pictureId: Long): Boolean {
        val exercise = get(exerciseId)
        val pictures = exercise.pictures
        if (pictures != null) {
            val removed = pictures.removeIf { picture -> picture.id == pictureId }
            if (removed) {
                save(exercise)
            }
            return removed
        }
        return false
    }

    override fun addVideo(exerciseId: Long, videoId: Long): Video {
        val exercise = get(exerciseId)
        val video = videoService.get(videoId)
        val videos = exercise.videos
        if (videos == null) {
            exercise.videos = mutableListOf(video)
        } else {
            videos.add(video)
        }
        save(exercise)
        return video
    }

    override fun removeVideo(exerciseId: Long, videoId: Long): Boolean {
        val exercise = get(exerciseId)
        val videos = exercise.videos
        if (videos != null) {
            val removed = videos.removeIf { video -> video.id == videoId }
            if (removed) {
                save(exercise)
            }
            return removed
        }
        return false
    }
}
