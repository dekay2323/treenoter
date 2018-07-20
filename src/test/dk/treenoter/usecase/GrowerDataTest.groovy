package dk.treenoter.usecase

import dk.treenoter.entities.Data
import spock.lang.Specification

class GrowerDataTest extends Specification {
    GrowerData growerData = new GrowerData()
    Outputer outputer = new Outputer()
        
    def "create data"() {
        when:
        final Data root = growerData.create('subject', 'text')
        
        then:
        root
        root.subject == 'subject'
        root.text == 'text'
        outputer.toString(root) == "subject"
    }

    def "create data no text"() {
        when:
        final Data root = growerData.create('subject')

        then:
        root
        root.subject == 'subject'
        root.text == ''

        System.out.println outputer.toString(root)
    }
}
