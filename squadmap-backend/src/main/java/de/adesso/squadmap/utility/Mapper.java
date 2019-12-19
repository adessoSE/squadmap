package de.adesso.squadmap.utility;

public interface Mapper<I, O> {

    O map(I i);
}
