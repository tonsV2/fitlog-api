package dk.fitfit.fitlog.service

import dk.fitfit.fitlog.domain.Round
import dk.fitfit.fitlog.domain.RoundExercise
import dk.fitfit.fitlog.domain.Workout
import dk.fitfit.fitlog.service.core.CrudService
import dk.fitfit.fitlog.service.core.UpdatedAfterService
import java.time.LocalDateTime

interface WorkoutService : CrudService<Workout, Long>, UpdatedAfterService<Workout, LocalDateTime> {
    fun update(id: Long, workout: Workout): Workout
    fun save(workoutId: Long, round: Round): Round
    fun save(roundId: Long, roundExercise: RoundExercise): RoundExercise
}
