package dk.fitfit.fitlog.domain.dto

import java.time.LocalDateTime

class UserRequest(
        val created: LocalDateTime,
        val id: Long
)
