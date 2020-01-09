import {Pipe, PipeTransform} from '@angular/core';
import {EmployeeModel} from '../models/employee.model';

interface FilterSettings {
  searchText: string;
  hideExternal?: boolean;
}
@Pipe({
  name: 'filterEmployees'
})
export class FilterEmployeesPipe implements PipeTransform {
  transform(employeeList: EmployeeModel[], filter: FilterSettings): EmployeeModel[] {
    if (!employeeList) { return []; }
    if (!filter.searchText) {
      if (!filter.hideExternal) {
        return employeeList;
      }
    }
    if (filter.searchText && filter.searchText.length > 0) {
      employeeList = employeeList.filter(employee => {
        if (employee.firstName.toLowerCase().includes(filter.searchText)) {
          return employee;
        }
      });
    }
    if (filter.hideExternal) {
      employeeList = employeeList.filter(employee => {
        if (!employee.isExternal) {
          return employee;
        }
      });
    }
    return employeeList;
  }
}
