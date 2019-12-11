import { Pipe, PipeTransform } from '@angular/core';
import {EmployeeModel} from '../models/employee.model';
@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {
  transform(employeeList: EmployeeModel[], searchText: string): any[] {
    if (!employeeList) { return []; } {
      if (!searchText) {
        return employeeList;
      }
      searchText = searchText.toLowerCase();
      return employeeList.filter( employee => {
        return employee.firstName.toLowerCase().includes(searchText);
      });
    }
  }
}
