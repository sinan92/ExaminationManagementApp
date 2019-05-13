import {Component, Input, OnInit} from '@angular/core';
import {User} from '../models/user';
import {saveAs} from 'file-saver';
import {UsersService} from '../services/users.service';
import {FinishedExamService} from '../services/finished-exam.service';

@Component({
  selector: 'app-supervisor-overzicht',
  templateUrl: './supervisor-overzicht.component.html',
  styleUrls: ['./supervisor-overzicht.component.css']
})
export class SupervisorOverzichtComponent implements OnInit {
  @Input() studenten: User[] = [];

  constructor(private serv: UsersService, private finishedExams: FinishedExamService) {  }

  ngOnInit() {
    this.serv.getStudenten().subscribe(data => this.studenten = data);
  }

  downloadAllFinishedExams() {
    this.finishedExams.getAllFinishedExamsFile().subscribe((file) => {
      saveAs(file, 'Examens.zip');
    });
  }

}
