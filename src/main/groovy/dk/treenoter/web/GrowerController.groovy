package dk.treenoter.web

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.HttpStatus


@Controller("/grower")
class GrowerController {

    @Get("/")
    HttpStatus index() {
        return HttpStatus.OK
    }
}