package dk.fitfit.fitlog.service

import dk.fitfit.fitlog.domain.Round
import java.time.LocalDateTime

interface RoundService : CrudService<Round, Long> {
    fun update(id: Long, round: Round): Round
    fun findAllAfter(updatedDateTime: LocalDateTime): Iterable<Round>
}
