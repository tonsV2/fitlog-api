package dk.fitfit.fitlog.controller

import dk.fitfit.fitlog.domain.assembler.toWorkout
import dk.fitfit.fitlog.domain.assembler.toWorkoutResponse
import dk.fitfit.fitlog.dto.WorkoutRequest
import dk.fitfit.fitlog.dto.WorkoutResponse
import dk.fitfit.fitlog.service.ExerciseService
import dk.fitfit.fitlog.service.UserService
import dk.fitfit.fitlog.service.WorkoutService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller
class WorkoutController(private val userService: UserService, private val workoutService: WorkoutService, private val exerciseService: ExerciseService) {
    @Post("/workouts")
    fun save(workoutRequest: WorkoutRequest, principal: Principal): WorkoutResponse {
        val user = userService.getByEmail(principal.name)
        val workout = workoutRequest.toWorkout(user)
        return workoutService.save(workout).toWorkoutResponse()
    }

    @Get("/workouts/{id}")
    fun get(id: Long) = workoutService.get(id).toWorkoutResponse()

    @Get("/workouts")
    fun findAll(updatedTimestamp: Long?) = if (updatedTimestamp != null) {
        // TODO: Why do I have to add one millisecond to make this work?
        val epochMilli = Instant.ofEpochMilli(updatedTimestamp + 1)
        val updated = LocalDateTime.ofInstant(epochMilli, ZoneOffset.UTC)
        workoutService.findAllAfter(updated)
    } else {
        workoutService.findAll()
    }.map { it.toWorkoutResponse() }

    @Put("/workouts/{id}")
    fun update(id: Long, workoutRequest: WorkoutRequest, principal: Principal): WorkoutResponse {
        val user = userService.getByEmail(principal.name)
        val workout = workoutRequest.toWorkout(user, id)
        return workoutService.update(id, workout).toWorkoutResponse()
    }

    @Delete("/workouts/{id}")
    fun delete(id: Long, principal: Principal): HttpResponse<Any> = userService.getByEmail(principal.name).let {
        val workout = workoutService.get(id)
        return@let if (workout.creator == it) {
            workoutService.delete(id)
            HttpResponse.ok()
        } else {
            HttpResponse.notAllowed()
        }
    }
}
