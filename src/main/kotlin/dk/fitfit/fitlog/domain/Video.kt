package dk.fitfit.fitlog.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import dk.fitfit.fitlog.domain.core.BaseEntity
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Video(
        val url: String,
        @JsonIgnore
        @ManyToOne
        val creator: User,
        id: Long = 0
) : BaseEntity(id)
