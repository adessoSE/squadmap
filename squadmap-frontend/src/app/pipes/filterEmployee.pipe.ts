import {Pipe, PipeTransform} from '@angular/core';
import {EmployeeModel} from '../models/employee.model';

interface FilterSettings {
  searchText: string;
}

@Pipe({
  name: 'filterEmployees'
})
export class FilterEmployeePipe implements PipeTransform {
  transform(employeeList: EmployeeModel[], filter: FilterSettings): EmployeeModel[] {
    if (!employeeList) { return []; }
    if (!filter.searchText) {
      return employeeList;
    }
    return employeeList.filter(employee => {
      if (filter.searchText) {
        if (employee.firstName.toLowerCase().includes(filter.searchText)) {
          return employee;
        }
      }
    });
  }
}
