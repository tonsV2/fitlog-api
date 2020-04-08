package dk.fitfit.fitlog.repository

import dk.fitfit.fitlog.domain.Exercise
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface ExerciseRepository : CrudRepository<Exercise, Long>
