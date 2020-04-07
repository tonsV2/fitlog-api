package dk.fitfit.fitlog.repository

import dk.fitfit.fitlog.domain.User
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun findByEmail(email: String): User?
}
