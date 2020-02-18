import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {FeatherModule} from 'angular-feather';
import {allIcons} from 'angular-feather/icons';


@NgModule({
  declarations: [],
  imports: [
    FeatherModule.pick(allIcons)
  ],
  exports: [
    FeatherModule
  ]
})
export class IconsModule { }
