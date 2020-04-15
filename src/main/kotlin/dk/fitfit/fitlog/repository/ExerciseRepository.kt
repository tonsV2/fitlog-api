package dk.fitfit.fitlog.repository

import dk.fitfit.fitlog.domain.Exercise
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.time.LocalDateTime

@Repository
interface ExerciseRepository : CrudRepository<Exercise, Long> {
    fun findByUpdatedAfter(updatedDateTime: LocalDateTime): Iterable<Exercise>
}
