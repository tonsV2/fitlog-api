package dk.fitfit.fitlog.service

import dk.fitfit.fitlog.domain.RoundExercise
import java.time.LocalDateTime

interface RoundExerciseService : CrudService<RoundExercise, Long> {
    fun update(id: Long, roundExercise: RoundExercise): RoundExercise
    fun findAllAfter(updatedDateTime: LocalDateTime): Iterable<RoundExercise>
}
