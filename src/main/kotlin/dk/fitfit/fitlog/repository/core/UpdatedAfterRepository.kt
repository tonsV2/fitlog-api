package dk.fitfit.fitlog.repository.core

import dk.fitfit.fitlog.domain.core.DateUpdatedEntity

interface UpdatedAfterRepository<E : DateUpdatedEntity<T>, T> {
    fun findByUpdatedAfter(updatedDateTime: T): Iterable<E>
}
