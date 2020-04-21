package dk.fitfit.fitlog.util

import dk.fitfit.fitlog.configuration.AuthenticationConfiguration
import dk.fitfit.fitlog.domain.Role
import dk.fitfit.fitlog.domain.User
import dk.fitfit.fitlog.service.RoleService
import dk.fitfit.fitlog.service.UserService
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.discovery.event.ServiceStartedEvent
import io.micronaut.scheduling.annotation.Async
import mu.KotlinLogging
import javax.inject.Singleton

private val logger = KotlinLogging.logger {}

@Singleton
open class DataLoader(private val authenticationConfiguration: AuthenticationConfiguration,
                      private val userService: UserService,
                      private val roleService: RoleService) : ApplicationEventListener<ServiceStartedEvent> {
    @Async
    override fun onApplicationEvent(event: ServiceStartedEvent) {
        val role = roleService.save(Role(Role.ADMIN))
        val roles = roleService.findAll()

        val adminUser = User(authenticationConfiguration.adminUserEmail, "")
        adminUser.roles.add(role)
        val savedAdmin = userService.save(adminUser)

        val testUser = User(authenticationConfiguration.testUserEmail, "")
        val savedTest = userService.save(testUser)

        val users = userService.findAll()

        logger.info {
            """
                Creating admin role
                Role: ${role.name}
                All roles: ${roles.joinToString { it.name }}
                Creating admin user
                Email: ${savedAdmin.email}
                Password: ${authenticationConfiguration.adminUserPassword}
                Creating test user
                Email: ${savedTest.email}
                Password: ${authenticationConfiguration.testUserPassword}
                All users: ${users.joinToString { it.email }}
            """.trimIndent()
        }
    }
}
