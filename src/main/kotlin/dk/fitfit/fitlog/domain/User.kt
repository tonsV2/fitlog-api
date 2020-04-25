package dk.fitfit.fitlog.domain

import dk.fitfit.fitlog.domain.core.BaseEntity
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
        id: Long = 0
) : BaseEntity(id)
