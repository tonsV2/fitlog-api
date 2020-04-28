package dk.fitfit.fitlog.service

import dk.fitfit.fitlog.domain.RoundExercise
import dk.fitfit.fitlog.service.core.CrudService
import dk.fitfit.fitlog.service.core.UpdatedAfterService
import java.time.LocalDateTime

interface RoundExerciseService : CrudService<RoundExercise, Long>, UpdatedAfterService<RoundExercise, LocalDateTime> {
    fun update(id: Long, roundExercise: RoundExercise): RoundExercise
}
