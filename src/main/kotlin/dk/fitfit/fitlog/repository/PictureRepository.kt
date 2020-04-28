package dk.fitfit.fitlog.repository

import dk.fitfit.fitlog.domain.Picture
import dk.fitfit.fitlog.repository.core.UpdatedAfterRepository
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.time.LocalDateTime
import javax.inject.Named

@Repository
@Named("PictureRepository")
interface PictureRepository : CrudRepository<Picture, Long>, UpdatedAfterRepository<Picture, LocalDateTime>
