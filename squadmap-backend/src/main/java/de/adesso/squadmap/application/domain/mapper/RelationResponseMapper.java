package de.adesso.squadmap.application.domain.mapper;

import de.adesso.squadmap.application.domain.WorkingOn;

import java.util.List;

public interface RelationResponseMapper<I, O> {

    O mapToResponseEntity(I i, List<WorkingOn> employeeWorkingOns, List<WorkingOn> projectWorkingOns);
}
