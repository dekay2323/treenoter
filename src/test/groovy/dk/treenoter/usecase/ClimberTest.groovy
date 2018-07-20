package dk.treenoter.usecase

import dk.treenoter.entities.Data
import dk.treenoter.entities.Node
import spock.lang.Specification

class ClimberTest extends Specification {
    final GrowerData growerData = new GrowerData()
    final GrowerNode growerNode = new GrowerNode()
    final Climber climber = new Climber()
    
    Data tomCruise
    Data georgeClooney
    Data seanBean

    Node people
    Node actors
    Node producers
    
    def setup() {
        tomCruise = growerData.create('Tom Cruise', 'Thomas Cruise is an American actor and producer.')
        georgeClooney = growerData.create('George Clooney', 'George Timothy Clooney (born May 6, 1961) is an American actor, director, producer, screenwriter, and businessman')
        seanBean = growerData.create('Sean Bean', 'Shaun Mark Bean (born 17 April 1959), known professionally as Sean Bean is an English actor')
        
        people = growerNode.createRoot('people')
        actors = growerNode.addNode(people, 'actors')
        actors.data.addAll([tomCruise, georgeClooney, seanBean])
        producers = growerNode.addNode(people, 'producers')
        producers.data.addAll([tomCruise, georgeClooney])
    }
    
    def "go to the top root of a node"() {
        when:
        final Node result = climber.goToRoot(producers)
        
        then:
        result == people
        climber.goToRoot(actors) == people
        climber.goToRoot(people) == people
    }
    
    def "flatten a tree into a list of nodes"() {
        when:
        final List<Node> result = climber.flattenToNodes(people)

        then:
        result
        result.size() == 3
        result.find {it.metadata == 'people'}
        result.find {it.metadata == 'actors'}
        result.find {it.metadata == 'producers'}
        climber.flattenToNodes(actors).size() == 1
    }

    def "flatten a tree into a list of data"() {
        when:
        final List<Data> result = climber.flattenToData(people)

        then:
        result
        result.size() == 3
        result.find {it.subject == 'Tom Cruise'}
        result.find {it.subject == 'George Clooney'}
        result.find {it.subject == 'Sean Bean'}
        climber.flattenToData(actors).size() == 3
        climber.flattenToData(producers).size() == 2
    }
    
    def "find exact by metadata"() {
        when:
        final List<Node> nodes = climber.findExactMetadata(people, 'producers')
        
        then:
        nodes
        nodes.size() == 1
        nodes.metadata[0] == 'producers'
    }

    def "find contains returns more than one by metadata"() {
        when:
        final List<Node> nodes = climber.findContainsMetadata(people, 'p')

        then:
        nodes
        nodes.size() == 2
        nodes.metadata[0] == 'people'
        nodes.metadata[1] == 'producers'
    }

    def "find exact data by subject"() {
        when:
        final List<Data> nodes = climber.findExactSubject(people, 'George Clooney')

        then:
        nodes
        nodes.size() == 1
        nodes[0].subject == 'George Clooney'
    }

    def "find contains data by subject"() {
        when:
        final List<Data> nodes = climber.findContainsSubject(people, 'o')

        then:
        nodes
        nodes.size() == 2
        nodes[0].subject == 'George Clooney'
        nodes[1].subject == 'Tom Cruise'
    }

    def "find exact data by text"() {
        when:
        final List<Data> nodes = climber.findExactText(people, 'Thomas Cruise is an American actor and producer.')

        then:
        nodes
        nodes.size() == 1
        nodes[0].subject == 'Tom Cruise'
    }

    def "find contains data by text"() {
        when:
        final List<Data> nodes = climber.findContainsText(people, 'American')

        then:
        nodes
        nodes.size() == 2
        nodes[0].subject == 'George Clooney'
        nodes[1].subject == 'Tom Cruise'
    }

}
