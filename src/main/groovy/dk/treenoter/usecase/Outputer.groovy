package dk.treenoter.usecase

import dk.treenoter.entities.Data
import dk.treenoter.entities.Node

class Outputer {
    String toString(final Node node) {
        assert node
        return "${node.getClass()}\n${node.convertToString()}"    
    }

    String toString(final Data data) {
        assert data
        return "${data.convertToString()}"
    }
}
