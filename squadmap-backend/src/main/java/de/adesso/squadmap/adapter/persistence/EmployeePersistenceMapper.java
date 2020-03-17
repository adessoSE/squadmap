package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
class EmployeePersistenceMapper implements PersistenceMapper<Employee, EmployeeNeo4JEntity> {

    public EmployeeNeo4JEntity mapToNeo4JEntity(Employee employee) {
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

    public Employee mapToDomainEntity(EmployeeNeo4JEntity employeeNeo4JEntity) {
        return new Employee(
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
