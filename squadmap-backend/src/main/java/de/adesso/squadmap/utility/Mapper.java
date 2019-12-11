package de.adesso.squadmap.utility;

import org.springframework.stereotype.Component;

public interface Mapper<I,O> {

    public O map(I i);
}
