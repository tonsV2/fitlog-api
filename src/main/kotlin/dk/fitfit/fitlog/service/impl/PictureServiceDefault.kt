package dk.fitfit.fitlog.service.impl

import dk.fitfit.fitlog.repository.PictureRepository
import dk.fitfit.fitlog.service.PictureService
import javax.inject.Singleton

@Singleton
class PictureServiceDefault(override val repository: PictureRepository) : PictureService
