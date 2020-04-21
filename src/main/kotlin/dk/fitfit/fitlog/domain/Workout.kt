package dk.fitfit.fitlog.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.FetchType.EAGER

@Entity
class Workout(
        val name: String,
        val description: String,
        @OneToMany(fetch = EAGER)
        @Fetch(FetchMode.SUBSELECT)
        var rounds: MutableList<Round>? = null,
        @JsonIgnore
        @ManyToOne
        val creator: User,
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        val id: Long = 0,
        @DateCreated
        var created: LocalDateTime = LocalDateTime.MIN,
        @DateUpdated
        var updated: LocalDateTime = LocalDateTime.MIN
)
