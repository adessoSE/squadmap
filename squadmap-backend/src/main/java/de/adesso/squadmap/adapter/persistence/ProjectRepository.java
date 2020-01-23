package de.adesso.squadmap.adapter.persistence;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ProjectRepository extends Neo4jRepository<ProjectNeo4JEntity, Long> {

    boolean existsByTitle(String title);
}
