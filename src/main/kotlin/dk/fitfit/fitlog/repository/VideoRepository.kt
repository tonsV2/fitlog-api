package dk.fitfit.fitlog.repository

import dk.fitfit.fitlog.domain.Video
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface VideoRepository : CrudRepository<Video, Long>
