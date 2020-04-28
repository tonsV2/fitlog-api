package dk.fitfit.fitlog.repository

import dk.fitfit.fitlog.domain.RoundExercise
import dk.fitfit.fitlog.repository.core.UpdatedAfterRepository
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.time.LocalDateTime
import javax.inject.Named

@Repository
@Named("RoundExerciseRepository")
interface RoundExerciseRepository : CrudRepository<RoundExercise, Long>, UpdatedAfterRepository<RoundExercise, LocalDateTime>
