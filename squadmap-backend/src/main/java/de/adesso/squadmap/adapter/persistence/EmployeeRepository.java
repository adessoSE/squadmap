package de.adesso.squadmap.adapter.persistence;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
interface EmployeeRepository extends Neo4jRepository<EmployeeNeo4JEntity, Long> {

    boolean existsByEmail(String email);
}
