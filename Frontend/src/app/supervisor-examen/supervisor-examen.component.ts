import {Component, Input, OnInit} from '@angular/core';
import {Examen} from '../models/exam.model';
import {ExamService} from '../services/exams.service';

@Component({
  selector: 'app-supervisor-examen',
  templateUrl: './supervisor-examen.component.html',
  styleUrls: ['./supervisor-examen.component.css']
})
export class SupervisorExamenComponent implements OnInit {
  @Input() examens: Examen[] = [];

  constructor(private serv: ExamService) {  }

  ngOnInit() {
    this.serv.getExams().subscribe(data => this.examens = data);
  }

}
