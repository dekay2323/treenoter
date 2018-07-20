package dk.treenoter.usecase

import dk.treenoter.entities.Node

class GrowerNode {
    Node createRoot(final String metadata) {
        assert metadata

        final Node node = Node.createRoot(metadata)
        return node
    }

    Node addNode(final Node parent, final String metadata) {
        assert parent
        assert metadata

        final Node node = Node.addNode(parent, metadata)
        parent.children.add(node)
        return node
    }
}
