package dk.fitfit.fitlog.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import dk.fitfit.fitlog.domain.core.BaseEntity
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode.SUBSELECT
import javax.persistence.Entity
import javax.persistence.FetchType.EAGER
import javax.persistence.Lob
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class Exercise(
        val name: String,
        @Lob
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
        id: Long = 0
) : BaseEntity(id)
