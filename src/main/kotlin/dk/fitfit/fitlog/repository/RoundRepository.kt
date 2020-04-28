package dk.fitfit.fitlog.repository

import dk.fitfit.fitlog.domain.Round
import dk.fitfit.fitlog.repository.core.UpdatedAfterRepository
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.time.LocalDateTime
import javax.inject.Named

@Repository
@Named("RoundRepository")
interface RoundRepository : CrudRepository<Round, Long>, UpdatedAfterRepository<Round, LocalDateTime>
