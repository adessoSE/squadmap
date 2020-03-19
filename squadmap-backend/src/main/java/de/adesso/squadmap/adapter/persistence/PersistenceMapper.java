package de.adesso.squadmap.adapter.persistence;

interface PersistenceMapper<I, O> {

    O mapToNeo4JEntity(I i);

    I mapToDomainEntity(O o);
}
