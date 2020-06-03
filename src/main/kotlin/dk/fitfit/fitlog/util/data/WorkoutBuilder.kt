package dk.fitfit.fitlog.util.data

import dk.fitfit.fitlog.domain.Round
import dk.fitfit.fitlog.domain.User
import dk.fitfit.fitlog.domain.Workout

class WorkoutBuilder(private val name: String, private val description: String, private val user: User) {
    private var rounds: MutableList<Round> = mutableListOf()

    fun addRound(roundBuilder: RoundBuilder): WorkoutBuilder {
        val round = roundBuilder.build(rounds.size)
        rounds.add(round)
        return this
    }

    fun build() = Workout(name, description, rounds, user)
}
