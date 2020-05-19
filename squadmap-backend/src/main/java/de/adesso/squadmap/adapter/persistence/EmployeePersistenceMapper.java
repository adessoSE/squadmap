package de.adesso.squadmap.adapter.persistence;

import de.adesso.squadmap.application.domain.Employee;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
class EmployeePersistenceMapper implements PersistenceMapper<Employee, EmployeeNeo4JEntity> {

    public EmployeeNeo4JEntity mapToNeo4JEntity(Employee employee) {
        return EmployeeNeo4JEntity.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .birthday(employee.getBirthday())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .isExternal(employee.getIsExternal())
                .image(employee.getImage())
                .projects(Collections.emptyList())
                .build();
    }

    public Employee mapToDomainEntity(EmployeeNeo4JEntity employeeNeo4JEntity) {
        return Employee.builder()
                .employeeId(employeeNeo4JEntity.getEmployeeId())
                .firstName(employeeNeo4JEntity.getFirstName())
                .lastName(employeeNeo4JEntity.getLastName())
                .birthday(employeeNeo4JEntity.getBirthday())
                .email(employeeNeo4JEntity.getEmail())
                .phone(employeeNeo4JEntity.getPhone())
                .image(employeeNeo4JEntity.getImage())
                .isExternal(employeeNeo4JEntity.getIsExternal())
                .build();
    }
}
