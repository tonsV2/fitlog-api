package dk.fitfit.fitlog.service.impl

import dk.fitfit.fitlog.domain.Round
import dk.fitfit.fitlog.domain.RoundExercise
import dk.fitfit.fitlog.domain.Workout
import dk.fitfit.fitlog.repository.RoundExerciseRepository
import dk.fitfit.fitlog.repository.WorkoutRepository
import dk.fitfit.fitlog.repository.core.UpdatedAfterRepository
import dk.fitfit.fitlog.service.RoundService
import dk.fitfit.fitlog.service.WorkoutService
import dk.fitfit.fitlog.util.merge
import java.time.LocalDateTime
import javax.inject.Named
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Singleton
@Transactional
class WorkoutServiceDefault(
        override val crudRepository: WorkoutRepository,
//        override val updatedAfterRepository: WorkoutRepository,
        @Named("WorkoutRepository") override val updatedAfterRepository: UpdatedAfterRepository<Workout, LocalDateTime>,
        private val entityManager: EntityManager,
        private val roundService: RoundService,
        private val roundExerciseRepository: RoundExerciseRepository
) : WorkoutService {
// TODO: Should be moved to CrudService
// TODO: Should not use entityManager
    override fun update(id: Long, workout: Workout): Workout {
        val existing = get(id)
        val updated = existing.merge(workout)
        return entityManager.merge(updated)
//        return repository.save(updated)
    }

    override fun save(roundId: Long, roundExercise: RoundExercise): RoundExercise {
        val round = roundService.get(roundId)
        val savedRoundExercise = roundExerciseRepository.save(roundExercise)
        val exercises = round.exercises

        if (exercises == null) {
            round.exercises = mutableListOf(savedRoundExercise)
        } else {
            exercises.add(savedRoundExercise)
        }
        roundService.save(round)

        return savedRoundExercise
    }

    override fun save(workoutId: Long, round: Round): Round {
        val workout = get(workoutId)
        val saveRound = roundService.save(round)

        val rounds = workout.rounds
        if (rounds == null) {
            workout.rounds = mutableListOf(saveRound)
        } else {
            rounds.add(saveRound)
        }
        save(workout)

        return saveRound
    }
}
