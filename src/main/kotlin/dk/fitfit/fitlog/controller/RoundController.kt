package dk.fitfit.fitlog.controller

import dk.fitfit.fitlog.domain.User
import dk.fitfit.fitlog.domain.assembler.toRound
import dk.fitfit.fitlog.domain.assembler.toRoundResponse
import dk.fitfit.fitlog.dto.RoundRequest
import dk.fitfit.fitlog.dto.RoundResponse
import dk.fitfit.fitlog.service.RoundService
import dk.fitfit.fitlog.service.UserService
import dk.fitfit.fitlog.service.WorkoutService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller
class RoundController(private val userService: UserService, private val workoutService: WorkoutService, private val roundService: RoundService) {
    @Post("/workouts/{workoutId}/rounds")
    fun save(workoutId: Long, roundRequest: RoundRequest, principal: Principal): RoundResponse {
        val user = userService.getByEmail(principal.name)
        val workout = workoutService.get(workoutId)
        if (workout.creator.id != user.id) {
            throw WorkoutDoesNotBelongToUserException(user, workoutId)
        }
        val round = roundRequest.toRound()
        return workoutService.save(workoutId, round).toRoundResponse()
    }

    @Get("/rounds/{id}")
    fun get(id: Long) = roundService.get(id).toRoundResponse()

    @Get("/rounds")
    fun findAll(updatedTimestamp: Long?) = if (updatedTimestamp != null) {
        // TODO: Why do I have to add one millisecond to make this work?
        val epochMilli = Instant.ofEpochMilli(updatedTimestamp + 1)
        val updated = LocalDateTime.ofInstant(epochMilli, ZoneOffset.UTC)
        roundService.findAllAfter(updated)
    } else {
        roundService.findAll()
    }.map { it.toRoundResponse() }

    @Put("/rounds/{id}")
    fun update(id: Long, roundRequest: RoundRequest, principal: Principal): RoundResponse {
        // TODO: Ensure that only the creator of the workout can update it's rounds
        val round = roundRequest.toRound(id)
        return roundService.update(id, round).toRoundResponse()
    }

/*
    @Delete("/rounds/{id}")
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

class WorkoutDoesNotBelongToUserException(user: User, workoutId: Long) : RuntimeException("Injury does not belong to user (${user.id}, $workoutId)")
