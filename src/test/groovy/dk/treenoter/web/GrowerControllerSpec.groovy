package dk.treenoter.web

import dk.treenoter.adapter.DataAdapter;
import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class GrowerControllerSpec extends Specification {
    @Shared @AutoCleanup EmbeddedServer server = ApplicationContext.run(EmbeddedServer)
    @Shared @AutoCleanup HttpClient client = HttpClient.create(server.URL)

    def "test json create"() {
        when:
        DataAdapter createdData = client.toBlocking().retrieve(
                HttpRequest.POST('/grower/data',
                        [subject: 'a subject', text: 'some text']), DataAdapter)

        then:
        createdData.id
        createdData.subject == 'a subject'
        createdData.text == 'some text'
    }
}
