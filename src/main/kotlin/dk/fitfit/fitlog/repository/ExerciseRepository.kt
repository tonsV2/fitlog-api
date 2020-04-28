package dk.fitfit.fitlog.repository

import dk.fitfit.fitlog.domain.Exercise
import dk.fitfit.fitlog.repository.core.UpdatedAfterRepository
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.time.LocalDateTime
import javax.inject.Named

@Repository
@Named("ExerciseRepository")
interface ExerciseRepository : CrudRepository<Exercise, Long>, UpdatedAfterRepository<Exercise, LocalDateTime>
