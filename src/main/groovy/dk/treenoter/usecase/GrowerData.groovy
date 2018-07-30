package dk.treenoter.usecase

import dk.treenoter.entities.Data

import javax.inject.Singleton

@Singleton
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
