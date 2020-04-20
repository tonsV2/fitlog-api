package dk.fitfit.fitlog.service

import dk.fitfit.fitlog.domain.Round
import dk.fitfit.fitlog.domain.RoundExercise
import dk.fitfit.fitlog.domain.Workout
import java.time.LocalDateTime

interface WorkoutService : CrudService<Workout, Long> {
    fun update(id: Long, workout: Workout): Workout
    fun findAllAfter(updatedDateTime: LocalDateTime): Iterable<Workout>
    fun save(workoutId: Long, round: Round): Round
    fun save(roundId: Long, roundExercise: RoundExercise): RoundExercise
}
