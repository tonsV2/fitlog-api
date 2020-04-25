package dk.fitfit.fitlog.domain

import dk.fitfit.fitlog.domain.core.BaseEntity
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany

@Entity
class Round(
        val priority: Int,
        val repetitions: Int,
        val rest: Int,
        @OneToMany(fetch = FetchType.EAGER)
        @Fetch(FetchMode.SUBSELECT)
        var exercises: MutableList<RoundExercise>? = null,
        id: Long = 0
) : BaseEntity(id)
