package dk.fitfit.fitlog.controller

import dk.fitfit.fitlog.domain.assembler.toPicture
import dk.fitfit.fitlog.domain.assembler.toPictureResponse
import dk.fitfit.fitlog.domain.dto.PictureRequest
import dk.fitfit.fitlog.domain.dto.PictureResponse
import dk.fitfit.fitlog.service.PictureService
import dk.fitfit.fitlog.service.UserService
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
class PictureController(private val userService: UserService, private val pictureService: PictureService) {
    @Post("/pictures")
    fun save(pictureRequest: PictureRequest, principal: Principal): PictureResponse {
        val user = userService.getByEmail(principal.name)
        val picture = pictureRequest.toPicture(user)
        return pictureService.save(picture).toPictureResponse()
    }

    @Get("/pictures/{id}")
    fun get(id: Long) = pictureService.get(id).toPictureResponse()

    @Get("/pictures")
    fun findAll(): Iterable<PictureResponse> = pictureService.findAll().map { it.toPictureResponse() }

    @Delete("/pictures/{id}")
    fun delete(id: Long, principal: Principal): HttpResponse<Any> = userService.getByEmail(principal.name).let {
        val picture = pictureService.get(id)
        return@let if (picture.creator == it) {
            pictureService.delete(id)
            HttpResponse.ok()
        } else {
            HttpResponse.notAllowed()
        }
    }
/*
    @Put("/pictures/{id}")
    fun update(id: Long, pictureRequest: PictureRequest, principal: Principal): PictureResponse {
        val user = userService.getByEmail(principal.name)
        val picture = pictureRequest.toPicture(user, id)
        return pictureService.update(id, picture).toPictureResponse()
    }
*/
}
