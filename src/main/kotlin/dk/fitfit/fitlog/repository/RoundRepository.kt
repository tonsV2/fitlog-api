package dk.fitfit.fitlog.repository

import dk.fitfit.fitlog.domain.Round
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.time.LocalDateTime

@Repository
interface RoundRepository : CrudRepository<Round, Long> {
    fun findByUpdatedAfter(updatedDateTime: LocalDateTime): Iterable<Round>
}
