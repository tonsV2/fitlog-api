package dk.fitfit.fitlog.controller

import dk.fitfit.fitlog.domain.Role
import dk.fitfit.fitlog.domain.assembler.toUserResponse
import dk.fitfit.fitlog.domain.dto.UserResponse
import dk.fitfit.fitlog.service.UserService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal
import javax.annotation.security.RolesAllowed

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller
class UserController(private val userService: UserService) {
    @RolesAllowed(Role.ADMIN)
    @Get("/users")
    fun getUsers(): Iterable<UserResponse> = userService.findAll().map { it.toUserResponse() }

    @Get("/principal")
    fun getPrincipal(principal: Principal): Principal = principal
}
