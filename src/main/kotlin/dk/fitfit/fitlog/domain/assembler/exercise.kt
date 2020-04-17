package dk.fitfit.fitlog.domain.assembler

import dk.fitfit.fitlog.domain.Exercise
import dk.fitfit.fitlog.domain.User
import dk.fitfit.fitlog.domain.dto.ExerciseRequest
import dk.fitfit.fitlog.domain.dto.ExerciseResponse

fun Exercise.toExerciseResponse() = ExerciseResponse(name, description, creator.toUserResponse(), videos?.map { it.toVideoResponse() }, pictures?.map { it.toPictureResponse() }, id, created, updated)
fun ExerciseRequest.toExercise(user: User, id: Long = 0) = Exercise(name, description, creator = user, id = this.id ?: id)
