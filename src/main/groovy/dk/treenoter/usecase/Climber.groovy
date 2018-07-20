package dk.treenoter.usecase

import dk.treenoter.entities.Data
import dk.treenoter.entities.Node

class Climber {
    Node goToRoot(final Node node) {
        assert node
        Node currentNode = node
        while (!currentNode.isRoot()) {
            currentNode = currentNode.parent
        }
        currentNode
    }
    
    List<Node> flattenToNodes(final Node node) {
        assert node
        ([node] + node.children?.collect { flattenToNodes(it) }).flatten().toSorted {a,b -> a.metadata <=> b.metadata}
    }


    final List<Data> flattenToData(Node node) {
        assert node
        ([node.data] + node.children?.collect { flattenToData(it) }).flatten().unique().toSorted {a,b -> a.subject <=> b.subject}
    }
    
    List<Data> findExactSubject(final Node node, final String searchFor) {
        assert node
        assert searchFor
        List<Data> flattenToData = flattenToData(node)
        flattenToData.findAll {it?.subject?.equals(searchFor)}
    }

    List<Data> findContainsSubject(final Node node, final String searchFor) {
        assert node
        assert searchFor
        List<Data> flattenToData = flattenToData(node)
        flattenToData.findAll {it?.subject?.contains(searchFor)}
    }

    List<Data> findExactText(final Node node, final String searchFor) {
        assert node
        assert searchFor
        List<Data> flattenToData = flattenToData(node)
        flattenToData.findAll {it?.text?.equals(searchFor)}
    }

    List<Data> findContainsText(final Node node, final String searchFor) {
        assert node
        assert searchFor
        List<Data> flattenToData = flattenToData(node)
        flattenToData.findAll {it?.text?.contains(searchFor)}
    }

    List<Node> findExactMetadata(final Node node, final String searchFor) {
        assert node
        assert searchFor

        List<Node> flattenToNodes = flattenToNodes(node)
        flattenToNodes.findAll {it?.metadata.equals(searchFor)}
    }

    List<Node> findContainsMetadata(final Node node, final String searchFor) {
        assert node
        assert searchFor

        List<Node> flattenToNodes = flattenToNodes(node)
        flattenToNodes.findAll {it?.metadata.contains(searchFor)}
    }

}
