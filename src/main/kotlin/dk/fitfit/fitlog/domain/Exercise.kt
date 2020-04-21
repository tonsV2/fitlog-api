package dk.fitfit.fitlog.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode.SUBSELECT
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.FetchType.EAGER
import javax.persistence.GenerationType.SEQUENCE

@Entity
class Exercise(
        val name: String,
        val description: String,
        @JsonIgnore
        @ManyToOne
        val creator: User,
        @OneToMany(fetch = EAGER)
        @Fetch(SUBSELECT)
        var videos: MutableList<Video>? = null,
        @OneToMany(fetch = EAGER)
        @Fetch(SUBSELECT)
        var pictures: MutableList<Picture>? = null,
        @Id
        @GeneratedValue(strategy = SEQUENCE)
        val id: Long = 0,
        @DateCreated
        var created: LocalDateTime? = null,
        @DateUpdated
        var updated: LocalDateTime? = null
)
