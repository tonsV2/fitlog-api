package dk.fitfit.fitlog.domain.assembler

import dk.fitfit.fitlog.domain.Exercise
import dk.fitfit.fitlog.domain.User
import dk.fitfit.fitlog.dto.ExerciseRequest
import dk.fitfit.fitlog.dto.ExerciseResponse

fun Exercise.toExerciseResponse() = ExerciseResponse(name, description, creator.toUserResponse(), videos?.map { it.toVideoResponse() }, pictures?.map { it.toPictureResponse() }, id, created, updated)
fun ExerciseRequest.toExercise(user: User, id: Long = 0) = Exercise(name, description, creator = user, id = if (id != 0L) id else this.id)
