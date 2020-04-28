package dk.fitfit.fitlog.service.impl

import dk.fitfit.fitlog.domain.Round
import dk.fitfit.fitlog.repository.RoundRepository
import dk.fitfit.fitlog.repository.core.UpdatedAfterRepository
import dk.fitfit.fitlog.service.RoundService
import dk.fitfit.fitlog.util.merge
import java.time.LocalDateTime
import javax.inject.Named
import javax.inject.Singleton
import javax.persistence.EntityManager

@Singleton
class RoundServiceDefault(
        @Named("RoundRepository") override val updatedAfterRepository: UpdatedAfterRepository<Round, LocalDateTime>,
//        override val updatedAfterRepository: RoundRepository,
        override val crudRepository: RoundRepository,
        private val entityManager: EntityManager
) : RoundService {
// TODO: Should be moved to CrudService
// TODO: Should not use entityManager
    override fun update(id: Long, round: Round): Round {
        val existing = get(id)
        val updated = existing.merge(round)
        return entityManager.merge(updated)
//        return repository.save(updated)
    }
}
