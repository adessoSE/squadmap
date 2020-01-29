package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
class EmployeePersistenceMapper {

    EmployeeNeo4JEntity mapToNeo4JEntity(Employee employee) {
        return new EmployeeNeo4JEntity(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getBirthday(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getIsExternal(),
                employee.getImage(),
                new ArrayList<>());
    }

    Employee mapToDomainEntity(EmployeeNeo4JEntity employeeNeo4JEntity) {
        return Employee.withId(
                employeeNeo4JEntity.getEmployeeId(),
                employeeNeo4JEntity.getFirstName(),
                employeeNeo4JEntity.getLastName(),
                employeeNeo4JEntity.getBirthday(),
                employeeNeo4JEntity.getEmail(),
                employeeNeo4JEntity.getPhone(),
                employeeNeo4JEntity.getIsExternal(),
                employeeNeo4JEntity.getImage());
    }
}
