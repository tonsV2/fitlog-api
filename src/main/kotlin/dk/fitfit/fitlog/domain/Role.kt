package dk.fitfit.fitlog.domain

import dk.fitfit.fitlog.domain.core.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Role(
        @Column(nullable = false, unique = true)
        val name: String,
        id: Long = 0
) : BaseEntity(id) {
    companion object {
        const val ADMIN = "ROLE_ADMIN"
    }
}
