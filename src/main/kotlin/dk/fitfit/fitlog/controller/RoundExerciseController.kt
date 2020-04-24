package dk.fitfit.fitlog.controller

import dk.fitfit.fitlog.domain.assembler.toRoundExercise
import dk.fitfit.fitlog.domain.assembler.toRoundExerciseResponse
import dk.fitfit.fitlog.dto.RoundExerciseRequest
import dk.fitfit.fitlog.dto.RoundExerciseResponse
import dk.fitfit.fitlog.service.ExerciseService
import dk.fitfit.fitlog.service.RoundExerciseService
import dk.fitfit.fitlog.service.WorkoutService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller
class RoundExerciseController(private val workoutService: WorkoutService, private val exerciseService: ExerciseService, private val roundExerciseService: RoundExerciseService) {
    @Post("/rounds/{roundId}/exercises")
    fun save(roundId: Long, roundExerciseRequest: RoundExerciseRequest, principal: Principal): RoundExerciseResponse {
        // TODO: Ensure that only the workout creator can add exercises
        val exercise = exerciseService.get(roundExerciseRequest.exerciseId)
        val roundExercise = roundExerciseRequest.toRoundExercise(exercise)
        return workoutService.save(roundId, roundExercise).toRoundExerciseResponse()
    }

    @Get("/round-exercises/{id}")
    fun get(id: Long) = roundExerciseService.get(id).toRoundExerciseResponse()

    @Get("/round-exercises")
    fun findAll(updatedTimestamp: Long?) = if (updatedTimestamp != null) {
        // TODO: Why do I have to add one millisecond to make this work?
        val epochMilli = Instant.ofEpochMilli(updatedTimestamp + 1)
        val updated = LocalDateTime.ofInstant(epochMilli, ZoneOffset.UTC)
        roundExerciseService.findAllAfter(updated)
    } else {
        roundExerciseService.findAll()
    }.map { it.toRoundExerciseResponse() }

/*
    @Put("/round-exercises/{id}")
    fun update(id: Long, roundRequest: RoundRequest, principal: Principal): RoundResponse {
        // TODO: Ensure that only the creator of the workout can update it's exercises
        val round = roundRequest.toRound(id)
        return roundService.update(id, round).toRoundResponse()
    }
*/

/*
    @Delete("/round-exercises/{id}")
    fun delete(id: Long, principal: Principal): HttpResponse<Any> = userService.getByEmail(principal.name).let {
        val round = roundService.get(id)
        return@let if (round.creator == it) {
            roundService.delete(id)
            HttpResponse.ok()
        } else {
            HttpResponse.notAllowed()
        }
    }
 */
}
