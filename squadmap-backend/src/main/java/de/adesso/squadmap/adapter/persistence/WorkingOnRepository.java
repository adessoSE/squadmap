package de.adesso.squadmap.adapter.persistence;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
interface WorkingOnRepository extends Neo4jRepository<WorkingOnNeo4JEntity, Long> {

    @Query("MATCH (e:Employee)-[w:WORKING_ON]->(p:Project)" +
            "WHERE ID(e)={employeeId} AND ID(p)={projectId}" +
            "RETURN Count(w)>0")
    boolean existsByEmployeeAndProject(Long employeeId, Long projectId);

    @Query("MATCH (e:Employee)-[w:WORKING_ON]->(p:Project)" +
            "WHERE ID(e)={employeeId}" +
            "RETURN w, e, p")
    Iterable<WorkingOnNeo4JEntity> findAllByEmployeeId(Long employeeId);

    @Query("MATCH (e:Employee)-[w:WORKING_ON]->(p:Project)" +
            "WHERE ID(p)={projectId}" +
            "RETURN w, e, p")
    Iterable<WorkingOnNeo4JEntity> findAllByProjectId(Long projectId);
}
