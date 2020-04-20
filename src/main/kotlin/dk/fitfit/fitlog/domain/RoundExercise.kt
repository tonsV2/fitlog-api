package dk.fitfit.fitlog.domain

import dk.fitfit.fitlog.dto.ExerciseResponse
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
        var created: LocalDateTime = LocalDateTime.MIN,
        @DateUpdated
        var updated: LocalDateTime = LocalDateTime.MIN
)

class RoundExerciseRequest(
        val exerciseId: Long,
        val repetitions: Int,
        val maxTime: Int,
        val priority: Int,
        val id: Long = 0
)

class RoundExerciseResponse(
        val exercise: ExerciseResponse,
        val repetitions: Int,
        val maxTime: Int,
        val priority: Int,
        val id: Long,
        val created: LocalDateTime,
        val updated: LocalDateTime
)
