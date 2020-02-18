import {AfterViewInit, Component} from '@angular/core';
import * as Feather from 'feather-icons';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements AfterViewInit {
  title = 'squadmap-frontend';

  ngAfterViewInit(): void {
    Feather.replace();
  }
}
