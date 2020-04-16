package dk.fitfit.fitlog.domain.assembler

import dk.fitfit.fitlog.domain.User
import dk.fitfit.fitlog.domain.dto.UserResponse

fun User.toUserResponse() = UserResponse(created, id)
