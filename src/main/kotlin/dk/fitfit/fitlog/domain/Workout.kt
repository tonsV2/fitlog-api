package dk.fitfit.fitlog.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import dk.fitfit.fitlog.domain.core.BaseEntity
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.Entity
import javax.persistence.FetchType.EAGER
import javax.persistence.Lob
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class Workout(
        val name: String,
        @Lob
        val description: String,
        @OneToMany(fetch = EAGER)
        @Fetch(FetchMode.SUBSELECT)
        var rounds: MutableList<Round>? = null,
        @JsonIgnore
        @ManyToOne
        val creator: User,
        id: Long = 0
) : BaseEntity(id)
