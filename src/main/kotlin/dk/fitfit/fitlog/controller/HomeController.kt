package dk.fitfit.fitlog.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller
class HomeController {
    @Get("/")
    fun getHome() = "Hello World!"
}
