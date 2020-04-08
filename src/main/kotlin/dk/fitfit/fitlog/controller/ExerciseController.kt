package dk.fitfit.fitlog.controller

import dk.fitfit.fitlog.domain.*
import dk.fitfit.fitlog.domain.dto.ExerciseRequest
import dk.fitfit.fitlog.domain.dto.ExerciseResponse
import dk.fitfit.fitlog.domain.dto.PictureResponse
import dk.fitfit.fitlog.domain.dto.VideoResponse
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
import javax.annotation.security.RolesAllowed

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

    private fun Video.toVideoResponse() = VideoResponse(url, id)
    private fun Picture.toPictureResponse() = PictureResponse(url, id)
    private fun Exercise.toExerciseResponse() = ExerciseResponse(name, description, videos.map { it.toVideoResponse() }, pictures.map { it.toPictureResponse() }, id)
    private fun ExerciseRequest.toExercise(user: User) = Exercise(name, description, creator = user)
}
