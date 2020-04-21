package dk.fitfit.fitlog.domain.assembler

import dk.fitfit.fitlog.domain.User
import dk.fitfit.fitlog.domain.Workout
import dk.fitfit.fitlog.dto.WorkoutRequest
import dk.fitfit.fitlog.dto.WorkoutResponse

fun Workout.toWorkoutResponse() = WorkoutResponse(name, description, rounds?.map { it.toRoundResponse() }, creator.toUserResponse(), id, created, updated)
fun WorkoutRequest.toWorkout(user: User, id: Long = 0) = Workout(name, description, creator = user, id = if (id != 0L) id else this.id)
