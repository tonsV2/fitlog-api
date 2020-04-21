package dk.fitfit.fitlog.domain.assembler

import dk.fitfit.fitlog.domain.User
import dk.fitfit.fitlog.dto.UserResponse

fun User.toUserResponse() = UserResponse(name, created, id)
