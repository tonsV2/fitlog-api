package dk.fitfit.fitlog.service.impl

import dk.fitfit.fitlog.domain.RoundExercise
import dk.fitfit.fitlog.repository.RoundExerciseRepository
import dk.fitfit.fitlog.service.RoundExerciseService
import dk.fitfit.fitlog.util.merge
import java.time.LocalDateTime
import javax.inject.Singleton
import javax.persistence.EntityManager

@Singleton
class RoundExerciseServiceDefault(override val repository: RoundExerciseRepository, private val entityManager: EntityManager) : RoundExerciseService {
// TODO: Should be moved to CrudService
// TODO: Should not use entityManager
    override fun update(id: Long, round: RoundExercise): RoundExercise {
        val existing = get(id)
        val updated = existing.merge(round)
        return entityManager.merge(updated)
//        return repository.save(updated)
    }

    override fun findAllAfter(updatedDateTime: LocalDateTime): Iterable<RoundExercise> = repository.findByUpdatedAfter(updatedDateTime)
}
