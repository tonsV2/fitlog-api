package dk.fitfit.fitlog.repository

import dk.fitfit.fitlog.domain.Role
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface RoleRepository : CrudRepository<Role, Long> {
    fun findByName(name: String): Role?
}
