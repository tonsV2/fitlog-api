package dk.fitfit.fitlog.repository

import dk.fitfit.fitlog.domain.Video
import dk.fitfit.fitlog.repository.core.UpdatedAfterRepository
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.time.LocalDateTime
import javax.inject.Named

@Repository
@Named("VideoRepository")
interface VideoRepository : CrudRepository<Video, Long>, UpdatedAfterRepository<Video, LocalDateTime>
