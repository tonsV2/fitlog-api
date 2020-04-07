package dk.fitfit.fitlog.service

import dk.fitfit.fitlog.domain.User

interface UserService {
    fun save(user: User): User
    fun getByEmail(email: String): User
    fun findAll(): Iterable<User>
}
