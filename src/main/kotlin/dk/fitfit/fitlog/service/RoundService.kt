package dk.fitfit.fitlog.service

import dk.fitfit.fitlog.domain.Round
import dk.fitfit.fitlog.service.core.CrudService
import dk.fitfit.fitlog.service.core.UpdatedAfterService
import java.time.LocalDateTime

interface RoundService : CrudService<Round, Long>, UpdatedAfterService<Round, LocalDateTime> {
    fun update(id: Long, round: Round): Round
}
