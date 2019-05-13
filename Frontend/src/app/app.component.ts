import {Component, OnInit} from '@angular/core';
import {ExamService} from './services/exams.service';
import {Examen} from './models/exam.model';
import {UsersService} from './services/users.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [ExamService, UsersService]
})
export class AppComponent implements OnInit {
  title = 'app';
  examens: Examen[] = [];

  constructor(private serv: ExamService) {}

  ngOnInit() {
    this.serv.getExams().subscribe(data => this.examens = data);
  }
}
