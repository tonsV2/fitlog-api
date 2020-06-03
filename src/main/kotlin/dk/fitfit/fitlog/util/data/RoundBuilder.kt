package dk.fitfit.fitlog.util.data

import dk.fitfit.fitlog.domain.Exercise
import dk.fitfit.fitlog.domain.Round
import dk.fitfit.fitlog.domain.RoundExercise

class RoundBuilder(private val repetitions: Int, private val rest: Int = 0) {
    private var exercises: MutableList<RoundExercise> = mutableListOf()

    fun addExercise(exercise: Exercise, repetitions: Int, maxTime: Int = 0): RoundBuilder {
        exercises.add(RoundExercise(exercise, repetitions, maxTime, exercises.size))
        return this
    }

    fun build(priority: Int) = Round(priority, repetitions, rest, exercises)
}
