package dk.fitfit.fitlog.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.*

@Entity
class Exercise(
        val name: String,
        val description: String,
        @OneToMany(fetch = FetchType.EAGER)
        @Fetch(FetchMode.SUBSELECT)
        val videos: List<Video> = mutableListOf(),
        @OneToMany(fetch = FetchType.EAGER)
        @Fetch(FetchMode.SUBSELECT)
        val pictures: List<Picture> = mutableListOf(),
        @JsonIgnore
        @ManyToOne
        val creator: User,
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        val id: Long = 0
)
