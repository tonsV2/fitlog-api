package dk.fitfit.fitlog.service.impl

import dk.fitfit.fitlog.repository.ExerciseRepository
import dk.fitfit.fitlog.repository.VideoRepository
import dk.fitfit.fitlog.service.ExerciseService
import dk.fitfit.fitlog.service.VideoService
import javax.inject.Singleton

@Singleton
class ExerciseServiceDefault(override val repository: ExerciseRepository) : ExerciseService
