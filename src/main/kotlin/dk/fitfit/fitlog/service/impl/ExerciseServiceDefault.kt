package dk.fitfit.fitlog.service.impl

import dk.fitfit.fitlog.domain.Exercise
import dk.fitfit.fitlog.domain.Picture
import dk.fitfit.fitlog.domain.Video
import dk.fitfit.fitlog.repository.ExerciseRepository
import dk.fitfit.fitlog.service.ExerciseService
import dk.fitfit.fitlog.service.PictureService
import dk.fitfit.fitlog.service.VideoService
import dk.fitfit.fitlog.util.merge
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Singleton
@Transactional
class ExerciseServiceDefault(override val repository: ExerciseRepository,
                             private val videoService: VideoService,
                             private val pictureService: PictureService,
                             private val entityManager: EntityManager) : ExerciseService {

    override fun update(id: Long, exercise: Exercise): Exercise {
        val existing = get(id)
        val updated = existing.merge(exercise)
        return entityManager.merge(updated)
//        return save(updated)
    }

    override fun getPicture(id: Long): Picture = pictureService.get(id)

    override fun save(exerciseId: Long, picture: Picture): Picture {
        pictureService.save(picture)
        val exercise = get(exerciseId)
        exercise.pictures.add(picture)
        save(exercise)
        return picture
    }

    override fun deletePicture(exerciseId: Long, pictureId: Long) {
        val exercise = get(exerciseId)
        val picture = getPicture(pictureId)
        exercise.pictures.remove(picture)
        save(exercise)
//        pictureService.delete(pictureId)
    }

    override fun getVideo(id: Long): Video = videoService.get(id)

    override fun save(exerciseId: Long, video: Video): Video {
        videoService.save(video)
        val exercise = get(exerciseId)
        exercise.videos.add(video)
        save(exercise)
        return video
    }

    override fun deleteVideo(exerciseId: Long, videoId: Long) {
        val exercise = get(exerciseId)
        val video = getVideo(videoId)
        exercise.videos.remove(video)
        save(exercise)
//        videoService.delete(videoId)
    }
}
