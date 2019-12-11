import {Component, OnInit} from '@angular/core';
import {BsDropdownConfig} from 'ngx-bootstrap';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css'],
  providers: [{provide: BsDropdownConfig, useValue: {isAnimated: true, autoClose: true}}]
})
export class NavigationComponent implements OnInit {
  isCollapsed = true;

  constructor() {
  }

  ngOnInit() {
  }

}
