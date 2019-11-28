package de.adesso.squadmap.service;

import de.adesso.squadmap.models.Employee;
import de.adesso.squadmap.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Iterable<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findEmployeeById(long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public void createEmployee(Employee employee) {
        employee = new Employee(employee.getFirstName(),
                employee.getLastName(),
                employee.getBirthday(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getIsExternal());
        employeeRepository.save(employee);
    }

    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }
}
