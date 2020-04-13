package dk.fitfit.fitlog.domain

import javax.persistence.*
import javax.persistence.GenerationType.*

@Entity
class Role(
        @Column(nullable = false, unique = true)
        val name: String,
        @Id @GeneratedValue(strategy = SEQUENCE)
        val id: Long = 0
) {
    companion object {
        const val ADMIN = "ROLE_ADMIN"
    }
}
