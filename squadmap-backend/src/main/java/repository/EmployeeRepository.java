package repository;

import models.Employee;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface EmployeeRepository extends Neo4jRepository<Employee, Long> {

}
