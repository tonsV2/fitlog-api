package dk.fitfit.fitlog.repository

import dk.fitfit.fitlog.domain.Workout
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.time.LocalDateTime

@Repository
interface WorkoutRepository : CrudRepository<Workout, Long> {
    fun findByUpdatedAfter(updatedDateTime: LocalDateTime): Iterable<Workout>
}
