package dk.fitfit.fitlog.domain

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users") // Postgres doesn't like the table name "user"
class User(
        @Column(nullable = false, unique = true) val email: String,
        val name: String,
        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "users_roles",
                joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
        val roles: MutableList<Role> = mutableListOf(),
        @DateCreated
        var created: LocalDateTime = LocalDateTime.MIN,
        @DateUpdated
        var updated: LocalDateTime = LocalDateTime.MIN,
        @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) val id: Long = 0
)
