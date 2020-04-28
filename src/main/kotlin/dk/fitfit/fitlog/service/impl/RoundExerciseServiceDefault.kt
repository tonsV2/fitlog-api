package dk.fitfit.fitlog.service.impl

import dk.fitfit.fitlog.domain.RoundExercise
import dk.fitfit.fitlog.repository.RoundExerciseRepository
import dk.fitfit.fitlog.repository.core.UpdatedAfterRepository
import dk.fitfit.fitlog.service.RoundExerciseService
import dk.fitfit.fitlog.util.merge
import java.time.LocalDateTime
import javax.inject.Named
import javax.inject.Singleton
import javax.persistence.EntityManager

@Singleton
class RoundExerciseServiceDefault(
        @Named("RoundExerciseRepository") override val updatedAfterRepository: UpdatedAfterRepository<RoundExercise, LocalDateTime>,
        override val crudRepository: RoundExerciseRepository,
        private val entityManager: EntityManager
) : RoundExerciseService {
// TODO: Should be moved to CrudService
// TODO: Should not use entityManager
    override fun update(id: Long, round: RoundExercise): RoundExercise {
        val existing = get(id)
        val updated = existing.merge(round)
        return entityManager.merge(updated)
//        return repository.save(updated)
    }
}
