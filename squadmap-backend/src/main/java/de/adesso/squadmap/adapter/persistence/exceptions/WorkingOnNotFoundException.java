package de.adesso.squadmap.adapter.persistence.exceptions;

import de.adesso.squadmap.application.domain.exceptions.NotFoundException;

public class WorkingOnNotFoundException extends NotFoundException {
    public WorkingOnNotFoundException() {
        super("No matching workingOn relation was found");
    }
}
