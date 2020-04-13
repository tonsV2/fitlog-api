package dk.fitfit.fitlog.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode.SUBSELECT
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
        val videos: MutableList<Video> = mutableListOf(),
        @OneToMany(fetch = EAGER)
        @Fetch(SUBSELECT)
        val pictures: MutableList<Picture> = mutableListOf(),
        @Id
        @GeneratedValue(strategy = SEQUENCE)
        val id: Long = 0
)
