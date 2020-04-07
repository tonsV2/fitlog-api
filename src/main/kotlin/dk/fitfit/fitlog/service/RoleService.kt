package dk.fitfit.fitlog.service

import dk.fitfit.fitlog.domain.Role

interface RoleService {
    fun save(role: Role): Role
    fun findAll(): Iterable<Role>
    fun get(roleName: String): Role
}
