package dk.treenoter.entities

class Node {
    Node parent
    String metadata
    List<Node> children
    List<Data> data
    
    private Node() {}
    
    final boolean isRoot() {
        return parent == null
    }
    
    final boolean hasChildren() {
        return !children.isEmpty()
    }

    final boolean hasData() {
        return !data.isEmpty()
    }

    static Node createRoot(final String metadata) {
        assert metadata

        final Node node = new Node(parent: null, metadata: metadata, children: [], data: [])
        return node
    }

    static Node addNode(final Node parent, final String metadata) {
        assert parent
        assert metadata

        final Node node = new Node(parent: parent, metadata: metadata, children: [], data: [])
        return node
    }

    final String convertToString() {
        convToString("", true, "");
    }

    protected String convToString(final String prefix, final Boolean isTail, String result) {
        result = result + prefix + (isTail ? "└── " : "├── ") + this.toString() + " " + this?.data + "\n"
        for (int i = 0; i < children.size() - 1; i++) {
            result = children.get(i)
                    .convToString(prefix + (isTail ? "    " : "│   "), false, result)
        }
        if (children.size() > 0) {
            result = children.get(children.size() - 1)
                    .convToString(prefix + (isTail ? "    " : "│   "), true, result)
        }
        result
    }
    
    String toString() {
        "${metadata}"
    }
}
