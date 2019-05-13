import {Component, Input, OnInit} from '@angular/core';
import {User} from '../models/user';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-supervisor-header',
  templateUrl: './supervisor-header.component.html',
  styleUrls: ['./supervisor-header.component.css']
})
export class SupervisorHeaderComponent implements OnInit {
  @Input() supervisor: User;

  constructor(private auth: AuthService) { }

  ngOnInit() {
    this.auth.getLoggedInUser().subscribe((supervisor) => this.supervisor = supervisor);
  }

}
