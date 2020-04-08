package dk.fitfit.fitlog.service.impl

import dk.fitfit.fitlog.domain.User
import dk.fitfit.fitlog.repository.UserRepository
import dk.fitfit.fitlog.service.UserService
import javax.inject.Singleton

@Singleton
class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override fun save(user: User): User = userRepository.save(user)

    override fun getByEmail(email: String): User = userRepository.findByEmail(email) ?: throw UserNotFoundException(email)

    override fun findAll(): Iterable<User> = userRepository.findAll()
}

class UserNotFoundException(email: String) : RuntimeException("No user found with the email: $email")
