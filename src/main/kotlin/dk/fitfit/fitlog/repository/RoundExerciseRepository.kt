package dk.fitfit.fitlog.repository

import dk.fitfit.fitlog.domain.RoundExercise
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface RoundExerciseRepository : CrudRepository<RoundExercise, Long>
