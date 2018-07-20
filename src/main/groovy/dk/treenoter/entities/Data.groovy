package dk.treenoter.entities

class Data {
    String subject
    String text

    private Data() {}
    
    static Data create(final String subject, final String text) {
        assert subject

        new Data(subject: subject, text: text)
    }

    String toString() {
        "${subject}"
    }

    final String convertToString() {
        toString()
    }
}
