package dk.treenoter.usecase

import dk.treenoter.entities.Data

class GrowerData {
    Data create(final String subject, final String text) {
        assert subject

        Data.create(subject, text)
    }

    Data create(final String subject) {
        assert subject

        Data.create(subject, '')
    }
}
