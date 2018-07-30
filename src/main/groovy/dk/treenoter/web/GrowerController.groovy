package dk.treenoter.web

import dk.treenoter.adapter.DataAdapter
import dk.treenoter.db.PersistReddis
import dk.treenoter.entities.Data
import dk.treenoter.usecase.GrowerData
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put

import java.util.concurrent.CompletableFuture


@Controller("/grower")
class GrowerController {
    protected final PersistReddis persist
    protected final GrowerData growerData

    GrowerController(PersistReddis persist, GrowerData growerData) {
        this.persist = persist
        this.growerData = growerData
    }

    // curl -X POST localhost:8080/grower/data --header "Content-Type: application/json" -d '{"subject":"Kurt Vonnegut","text":"Some text"}'
    @Post("/data")
    CompletableFuture<HttpResponse<DataAdapter>> create(@Body CompletableFuture<DataAdapter> data) {
        return data.thenApply {
            assert it

            final Data createdData = growerData.create(it.subject, it.text)
            final DataAdapter dataAdapter = DataAdapter.buildFromEntity(createdData)
            
            return HttpResponse.created(persist.create(dataAdapter));
        }
    }
/*
    // curl -X PUT localhost:8080/data/1def89ef-140e-4220-98ea-5d5e894646a5  --header "Content-Type: application/json" -d '{"id":"1def89ef-140e-4220-98ea-5d5e894646a5", "subject":"Kurt Vonnegut","text":"Some text"}'
    @Put("/{id}")
    CompletableFuture<HttpResponse<DataAdapter>> update(String id, @Body CompletableFuture<DataAdapter> data) {
        return data.thenApply {
            assert id
            assert it

            it.id = id;

            return HttpResponse.created(persist.update(id, it));
        }
    }*/

    // curl -X GET localhost:8080/data/01fb64db-51f8-4843-b870-55ca8e5f573e
    @Get("/")
    CompletableFuture<HttpResponse<DataAdapter>> show(String id) {
            return HttpResponse.ok(persist.show(id));
    }
}