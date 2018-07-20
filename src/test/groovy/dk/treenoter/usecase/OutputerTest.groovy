package dk.treenoter.usecase

import dk.treenoter.entities.Data
import dk.treenoter.entities.Node
import spock.lang.Specification

class OutputerTest extends Specification {
    GrowerData growerData = new GrowerData()
    GrowerNode growerNode = new GrowerNode()
    Outputer outputer = new Outputer()
        
    def "output nodes to string"() {
        setup:
        Node person = growerNode.createRoot('person')
        Node actor = growerNode.addNode(person, 'actor')
        Node tv = growerNode.addNode(actor, 'tv')
        Node movie = growerNode.addNode(actor, 'movie')
        Node writer = growerNode.addNode(person, 'writer')
                
        when:
        String result = outputer.toString(person)
        System.out.println(result)

        then:
        result
        121 == result.size()
        result.find('person')
        result.find('actor')
        result.find('tv')
        result.find('movie')
        result.find('writer')
    }

    def "output data to string"() {
        setup:
        Data data = growerData.create('subject', 'text')
        
        when:
        String result = outputer.toString(data)
        System.out.println(result)

        then:
        result
        result == "subject"
    }
    
    def "output data and nodes"() {
        setup:
        Data tomCruise = growerData.create('Tom Cruise', 'Thomas Cruise is an American actor and producer.')
        Data georgeClooney = growerData.create('George Clooney', 'George Timothy Clooney (born May 6, 1961) is an American actor, director, producer, screenwriter, and businessman')
        Data seanBean = growerData.create('Sean Bean', 'Shaun Mark Bean (born 17 April 1959), known professionally as Sean Bean is an English actor')

        Node people = growerNode.createRoot('people')
        Node actors = growerNode.addNode(people, 'actors')
        actors.data.addAll([tomCruise, georgeClooney, seanBean])
        Node producers = growerNode.addNode(people, 'producers')
        producers.data.addAll([tomCruise, georgeClooney])

        when:
        String result = outputer.toString(people)
        System.out.println(result)
        
        then:
        result
    }
    
}
