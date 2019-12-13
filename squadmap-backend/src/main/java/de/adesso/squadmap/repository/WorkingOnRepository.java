package de.adesso.squadmap.repository;

import de.adesso.squadmap.domain.Employee;
import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.domain.WorkingOn;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingOnRepository extends Neo4jRepository<WorkingOn, Long> {

    @Query("MATCH (e:Employee)-[w:WORKING_ON]->(p:Project) " +
                    "WHERE ID(e)={employeeId} AND ID(p)={projectId}" +
                    "RETURN Count(w)>0")
    boolean existsByEmployeeAndProject(long employeeId, long projectId);
}
