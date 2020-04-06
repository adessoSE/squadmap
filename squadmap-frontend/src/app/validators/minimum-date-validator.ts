import {AbstractControl} from "@angular/forms";

export function minimumDateValidator(control: AbstractControl): {[key: string]: any} | null {
  const date: Date = new Date(control.value);
  if(date.getFullYear()<1900){
    return {'minimumDate':true};
  }else{
    return null;
  }
}
