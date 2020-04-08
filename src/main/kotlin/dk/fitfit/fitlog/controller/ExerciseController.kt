package dk.fitfit.fitlog.controller

import dk.fitfit.fitlog.domain.*
import dk.fitfit.fitlog.domain.dto.*
import dk.fitfit.fitlog.service.ExerciseService
import dk.fitfit.fitlog.service.UserService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller
class ExerciseController(private val userService: UserService, private val exerciseService: ExerciseService) {
    @Post("/exercises")
    fun save(exerciseRequest: ExerciseRequest, principal: Principal) {
        val user = userService.getByEmail(principal.name)
        val exercise = exerciseRequest.toExercise(user)
        exerciseService.save(exercise)
    }

    @Get("/exercises/{id}")
    fun get(id: Long) = exerciseService.get(id).toExerciseResponse()

    @Get("/exercises")
    fun findAll(): Iterable<ExerciseResponse> = exerciseService.findAll().map { it.toExerciseResponse() }

    @Delete("/exercises/{id}")
    fun delete(id: Long, principal: Principal): HttpResponse<Any> = userService.getByEmail(principal.name).let {
        val exercise = exerciseService.get(id)
        return@let if (exercise.creator == it) {
            exerciseService.delete(id)
            HttpResponse.ok()
        } else {
            HttpResponse.notAllowed()
        }
    }

    @Post("/exercises/{exerciseId}/videos")
    fun saveVideo(exerciseId: Long, videoRequest: VideoRequest, principal: Principal): VideoResponse {
        val user = userService.getByEmail(principal.name)
        val video = videoRequest.toVideo(user)
        return exerciseService.save(exerciseId, video).toVideoResponse()
    }

    @Delete("/exercises/{exerciseId}/videos/{videoId}")
    fun deleteVideo(exerciseId: Long, videoId: Long, principal: Principal): HttpResponse<Any> = userService.getByEmail(principal.name).let {
        val video = exerciseService.getVideo(videoId)
        return@let if (video.creator == it) {
            exerciseService.deleteVideo(exerciseId, videoId)
            HttpResponse.ok()
        } else {
            HttpResponse.notAllowed()
        }
    }

    @Post("/exercises/{exerciseId}/pictures")
    fun savePicture(exerciseId: Long, pictureRequest: PictureRequest, principal: Principal): PictureResponse {
        val user = userService.getByEmail(principal.name)
        val picture = pictureRequest.toPicture(user)
        return exerciseService.save(exerciseId, picture).toPictureResponse()
    }

    @Delete("/exercises/{exerciseId}/pictures/{pictureId}")
    fun deletePicture(exerciseId: Long, pictureId: Long, principal: Principal): HttpResponse<Any> = userService.getByEmail(principal.name).let {
        val picture = exerciseService.getPicture(pictureId)
        return@let if (picture.creator == it) {
            exerciseService.deletePicture(exerciseId, pictureId)
            HttpResponse.ok()
        } else {
            HttpResponse.notAllowed()
        }
    }

    private fun Video.toVideoResponse() = VideoResponse(url, id)
    private fun VideoRequest.toVideo(user: User) = Video(url, user)
    private fun Picture.toPictureResponse() = PictureResponse(url, id)
    private fun PictureRequest.toPicture(user: User) = Picture(url, user)
    private fun Exercise.toExerciseResponse() = ExerciseResponse(name, description, videos.map { it.toVideoResponse() }, pictures.map { it.toPictureResponse() }, id)
    private fun ExerciseRequest.toExercise(user: User) = Exercise(name, description, creator = user)
}
