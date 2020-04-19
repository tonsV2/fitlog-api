package dk.fitfit.fitlog.controller

import dk.fitfit.fitlog.domain.assembler.toVideo
import dk.fitfit.fitlog.domain.assembler.toVideoResponse
import dk.fitfit.fitlog.dto.VideoRequest
import dk.fitfit.fitlog.dto.VideoResponse
import dk.fitfit.fitlog.service.UserService
import dk.fitfit.fitlog.service.VideoService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller
class VideoController(private val userService: UserService, private val videoService: VideoService) {
    @Post("/videos")
    fun save(videoRequest: VideoRequest, principal: Principal): VideoResponse {
        val user = userService.getByEmail(principal.name)
        val video = videoRequest.toVideo(user)
        return videoService.save(video).toVideoResponse()
    }

    @Get("/videos/{id}")
    fun get(id: Long) = videoService.get(id).toVideoResponse()

    @Get("/videos")
    fun findAll(): Iterable<VideoResponse> = videoService.findAll().map { it.toVideoResponse() }

    @Delete("/videos/{id}")
    fun delete(id: Long, principal: Principal): HttpResponse<Any> = userService.getByEmail(principal.name).let {
        val video = videoService.get(id)
        return@let if (video.creator == it) {
            videoService.delete(id)
            HttpResponse.ok()
        } else {
            HttpResponse.notAllowed()
        }
    }

/*
    @Put("/videos/{id}")
    fun update(id: Long, videoRequest: VideoRequest, principal: Principal): VideoResponse {
        val user = userService.getByEmail(principal.name)
        val video = videoRequest.toVideo(user, id)
        return videoService.update(id, video).toVideoResponse()
    }
*/
}
