package dk.fitfit.fitlog.domain

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Round(
        val priority: Int,
        val repetitions: Int,
        val rest: Int,
        @OneToMany(fetch = FetchType.EAGER)
        @Fetch(FetchMode.SUBSELECT)
        var exercises: MutableList<RoundExercise>? = null,
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        val id: Long = 0,
        @DateCreated
        var created: LocalDateTime? = null,
        @DateUpdated
        var updated: LocalDateTime? = null
)
