import { Component, OnInit } from '@angular/core';
import {AuthService} from '../services/auth.service';

@Component({
  template: ''
})
export class LogoutComponent implements OnInit {

  constructor(private serve: AuthService) {}

  ngOnInit() {
    this.serve.logout();
  }

}
