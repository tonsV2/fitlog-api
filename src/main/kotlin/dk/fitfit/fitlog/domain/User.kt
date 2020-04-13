package dk.fitfit.fitlog.domain

import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.FetchType.*
import javax.persistence.GenerationType.*

@Entity
@Table(name = "users") // Postgres doesn't like the table name "user"
class User(
        @Column(nullable = false, unique = true)
        val email: String,
        val created: LocalDateTime = LocalDateTime.now(),
        @ManyToMany(fetch = EAGER)
        @JoinTable(name = "users_roles",
                joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
        val roles: MutableList<Role> = mutableListOf(),
        @Id @GeneratedValue(strategy = SEQUENCE)
        val id: Long = 0
)
