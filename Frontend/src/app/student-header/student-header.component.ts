import {Component, Input, OnInit} from '@angular/core';
import {AuthService} from '../services/auth.service';
import {User} from '../models/user';

@Component({
  selector: 'app-student-header',
  templateUrl: './student-header.component.html',
  styleUrls: ['./student-header.component.css']
})
export class StudentHeaderComponent implements OnInit {
  @Input() student: User;

  constructor(private auth: AuthService) { }

  ngOnInit() {
    this.auth.getLoggedInUser().subscribe((student) => this.student = student);
  }

}
