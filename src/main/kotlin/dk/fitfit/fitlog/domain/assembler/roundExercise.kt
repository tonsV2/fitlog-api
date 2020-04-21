package dk.fitfit.fitlog.domain.assembler

import dk.fitfit.fitlog.domain.Exercise
import dk.fitfit.fitlog.domain.RoundExercise
import dk.fitfit.fitlog.dto.RoundExerciseRequest
import dk.fitfit.fitlog.dto.RoundExerciseResponse

fun RoundExercise.toRoundExerciseResponse() = RoundExerciseResponse(exercise.toExerciseResponse(), repetitions, maxTime, priority, id, created, updated)
fun RoundExerciseRequest.toRoundExercise(exercise: Exercise, id: Long = 0) = RoundExercise(exercise, repetitions, maxTime, priority, if (id != 0L) id else this.id)
