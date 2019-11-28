package de.adesso.squadmap.repository;

import de.adesso.squadmap.models.WorkingOn;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingOnRepository extends Neo4jRepository<WorkingOn, Long> {
}
