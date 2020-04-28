package dk.fitfit.fitlog.service.impl

import dk.fitfit.fitlog.repository.VideoRepository
import dk.fitfit.fitlog.service.VideoService
import javax.inject.Singleton

@Singleton
class VideoServiceDefault(override val crudRepository: VideoRepository) : VideoService
