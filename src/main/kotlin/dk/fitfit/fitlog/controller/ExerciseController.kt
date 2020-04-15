package dk.fitfit.fitlog.controller

import dk.fitfit.fitlog.domain.Exercise
import dk.fitfit.fitlog.domain.Picture
import dk.fitfit.fitlog.domain.User
import dk.fitfit.fitlog.domain.Video
import dk.fitfit.fitlog.domain.dto.*
import dk.fitfit.fitlog.service.ExerciseService
import dk.fitfit.fitlog.service.UserService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller
class ExerciseController(private val userService: UserService, private val exerciseService: ExerciseService) {
    @Post("/exercises")
    fun save(exerciseRequest: ExerciseRequest, principal: Principal): ExerciseResponse {
        val user = userService.getByEmail(principal.name)
        val exercise = exerciseRequest.toExercise(user)
        return exerciseService.save(exercise).toExerciseResponse()
    }

    @Get("/exercises/{id}")
    fun get(id: Long) = exerciseService.get(id).toExerciseResponse()

    @Get("/exercises")
    fun findAll(updatedTimestamp: Long?) = if (updatedTimestamp != null) {
        val epochSecond = Instant.ofEpochSecond(updatedTimestamp)
        val updated = LocalDateTime.ofInstant(epochSecond, ZoneId.systemDefault())
        exerciseService.findAllAfter(updated)
    } else {
        exerciseService.findAll()
    }.map { it.toExerciseResponse() }

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

    @Post("/exercises/{exerciseId}/videos/{videoId}")
    fun addVideo(exerciseId: Long, videoId: Long, principal: Principal): HttpResponse<Any> {
        val user = userService.getByEmail(principal.name)
        val exercise = exerciseService.get(exerciseId)
        return if (exercise.creator.id == user.id) {
            exerciseService.addVideo(exerciseId, videoId)
            HttpResponse.ok()
        } else {
            HttpResponse.notAllowed()
        }
    }

    @Delete("/exercises/{exerciseId}/videos/{videoId}")
    fun removeVideo(exerciseId: Long, videoId: Long, principal: Principal): HttpResponse<Any> = userService.getByEmail(principal.name).let {
        val exercise = exerciseService.get(exerciseId)
        return@let if (exercise.creator.id == it.id) {
            exerciseService.removeVideo(exerciseId, videoId)
            HttpResponse.ok()
        } else {
            HttpResponse.notAllowed()
        }
    }

    @Post("/exercises/{exerciseId}/pictures/{pictureId}")
    fun addPicture(exerciseId: Long, pictureId: Long, principal: Principal): HttpResponse<Any> {
        val user = userService.getByEmail(principal.name)
        val exercise = exerciseService.get(exerciseId)
        return if (exercise.creator.id == user.id) {
            exerciseService.addPicture(exerciseId, pictureId)
            HttpResponse.ok()
        } else {
            HttpResponse.notAllowed()
        }
    }

    @Delete("/exercises/{exerciseId}/pictures/{pictureId}")
    fun deletePicture(exerciseId: Long, pictureId: Long, principal: Principal): HttpResponse<Any> = userService.getByEmail(principal.name).let {
        val exercise = exerciseService.get(exerciseId)
        return@let if (exercise.creator.id == it.id) {
            exerciseService.removePicture(exerciseId, pictureId)
            HttpResponse.ok()
        } else {
            HttpResponse.notAllowed()
        }
    }

    @Put("/exercises/{id}")
    fun update(id: Long, exerciseRequest: ExerciseRequest, principal: Principal): ExerciseResponse {
        val user = userService.getByEmail(principal.name)
        val exercise = exerciseRequest.toExercise(user, id)
        return exerciseService.update(id, exercise).toExerciseResponse()
    }

    private fun User.toUserResponse() = UserResponse(created, id)
    private fun Video.toVideoResponse() = VideoResponse(url, creator.toUserResponse(), id)
    private fun Picture.toPictureResponse() = PictureResponse(url, creator.toUserResponse(), id)
    private fun Exercise.toExerciseResponse() = ExerciseResponse(name, description, creator.toUserResponse(), videos?.map { it.toVideoResponse() }, pictures?.map { it.toPictureResponse() }, id, created, updated)
    private fun ExerciseRequest.toExercise(user: User, id: Long = 0) = Exercise(name, description, creator = user, id = this.id ?: id)
}
