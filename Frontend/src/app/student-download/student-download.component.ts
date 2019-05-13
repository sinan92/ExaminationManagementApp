import {Component, Input, OnInit} from '@angular/core';
import {Examen} from '../models/exam.model';
import {ExamService} from '../services/exams.service';
import {saveAs} from 'file-saver';

@Component({
  selector: 'app-student-download',
  templateUrl: './student-download.component.html',
  styleUrls: ['./student-download.component.css']
})
export class StudentDownloadComponent implements OnInit {
  @Input() examensStudent: Examen[] = [];

  constructor(private serv: ExamService) { }

  ngOnInit() {
    this.serv.getExams().subscribe(data => this.examensStudent = data);
  }

  getFinishedExamSkelet(id: number) {
    this.serv.getExamSkeletById(id).subscribe((file) => {
      this.serv.getExamById(id).subscribe((exam) => saveAs(file, exam.skelet));
    });
  }

}
