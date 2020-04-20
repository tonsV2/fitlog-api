package dk.fitfit.fitlog.domain.assembler

import dk.fitfit.fitlog.domain.Round
import dk.fitfit.fitlog.domain.RoundRequest
import dk.fitfit.fitlog.domain.RoundResponse

fun Round.toRoundResponse() = RoundResponse(priority, repetitions, rest, exercises?.map { it.toRoundExerciseResponse() }, id, created, updated)
fun RoundRequest.toRound(id: Long = 0) = Round(priority, repetitions, rest, id = if (id != 0L) id else this.id)
