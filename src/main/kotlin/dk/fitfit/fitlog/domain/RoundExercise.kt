package dk.fitfit.fitlog.domain

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class RoundExercise(
        @ManyToOne
        val exercise: Exercise,
        val repetitions: Int,
        val maxTime: Int,
        val priority: Int,
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        val id: Long = 0,
        @DateCreated
        var created: LocalDateTime? = null,
        @DateUpdated
        var updated: LocalDateTime? = null
)
