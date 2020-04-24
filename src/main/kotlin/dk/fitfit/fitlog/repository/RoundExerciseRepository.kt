package dk.fitfit.fitlog.repository

import dk.fitfit.fitlog.domain.RoundExercise
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.time.LocalDateTime

@Repository
interface RoundExerciseRepository : CrudRepository<RoundExercise, Long> {
    fun findByUpdatedAfter(updatedDateTime: LocalDateTime): Iterable<RoundExercise>
}
