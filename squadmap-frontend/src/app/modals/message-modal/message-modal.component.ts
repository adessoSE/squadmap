import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-message-modal',
  templateUrl: './message-modal.component.html',
})
export class MessageModalComponent implements OnInit {
  private message: string;

  constructor() { }

  ngOnInit() {
  }

}
