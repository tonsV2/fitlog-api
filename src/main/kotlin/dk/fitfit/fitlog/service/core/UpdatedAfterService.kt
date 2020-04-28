package dk.fitfit.fitlog.service.core

import dk.fitfit.fitlog.domain.core.DateUpdatedEntity
import dk.fitfit.fitlog.repository.core.UpdatedAfterRepository

interface UpdatedAfterService<E : DateUpdatedEntity<T>, T> {
    val updatedAfterRepository: UpdatedAfterRepository<E, T>
    fun findAllAfter(updatedDateTime: T): Iterable<E> = updatedAfterRepository.findByUpdatedAfter(updatedDateTime)
}
