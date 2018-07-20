package dk.treenoter.usecase

import dk.treenoter.entities.Node
import spock.lang.Specification

class GrowerNodeTest extends Specification {
    GrowerNode growerNode = new GrowerNode()
    Outputer outputer = new Outputer()
        
    def "create a new tree"() {
        when:
        final Node root = growerNode.createRoot('metadata')
        
        then:
        root
        root.parent == null
        root.isRoot()
        root.metadata == 'metadata'
        !root.hasChildren()
        !root.hasData()

        System.out.println outputer.toString(root)
    }
    
    def "add new node"() {
        setup:
        final Node root = growerNode.createRoot('metadata')

        when:
        final Node node = growerNode.addNode(root, 'tag')
        
        then:
        node
        node.parent == root
        node.metadata == 'tag'
        !node.hasChildren()
        !node.isRoot()
        
        root.hasChildren()
        !root.hasData()
        root.children.size() == 1
        
        System.out.println outputer.toString(root)
    }

    def "add new node to root and then child"() {
        setup:
        final Node root = growerNode.createRoot('authors')

        when:
        final Node books = growerNode.addNode(root, 'books')
        final Node movies = growerNode.addNode(root, 'movies')
        final Node actors = growerNode.addNode(movies, 'actors')

        then:
        root
        root.children.size() == 2
        root.children.find {it.metadata.equals('books')} == books
        root.children.find {it.metadata.equals('movies')} == movies
        root.children.find {it.metadata.equals('actors')} == null
        root.children.find {it.metadata.equals('movies')}?.children?.find {it.metadata.equals('actors')} == actors
        !books.hasChildren()
        movies.hasChildren()
        !actors.hasChildren()

        System.out.println outputer.toString(root)
    }

}
