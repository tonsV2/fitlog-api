package dk.fitfit.fitlog.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.persistence.GenerationType.*

@Entity
class Picture(
        val url: String,
        @JsonIgnore
        @ManyToOne
        val creator: User,
        @Id
        @GeneratedValue(strategy = SEQUENCE)
        val id: Long = 0
)
