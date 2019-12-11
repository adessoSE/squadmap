package de.adesso.squadmap.repository;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingOnRepository extends Neo4jRepository<WorkingOn, Long> {

    boolean existsByEmployeeAndProject(Employee employee, Project project);
}
