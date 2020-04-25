package dk.fitfit.fitlog.domain

import dk.fitfit.fitlog.domain.core.BaseEntity
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class RoundExercise(
        @ManyToOne
        val exercise: Exercise,
        val repetitions: Int,
        val maxTime: Int,
        val priority: Int,
        id: Long = 0
) : BaseEntity(id)
