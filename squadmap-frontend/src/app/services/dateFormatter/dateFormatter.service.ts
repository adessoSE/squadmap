import {Injectable} from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class DateFormatterService {
  constructor() {}

  /*
  Takes an Date Object and returns it as a string in format YYYY-MM-DD
  */

  public formatDate(date: Date): string {
    const d = new Date(date);
    let month = '' + (d.getMonth() + 1);
    let day = '' + d.getDate();
    const year = d.getFullYear();

    if (month.length < 2) {
      month = '0' + month;
    }
    if (day.length < 2) {
      day = '0' + day;
    }
    return [year, month, day].join('-');
  }
}
