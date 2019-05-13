import {Component, Input, OnInit} from '@angular/core';
import {ExamService} from '../services/exams.service';
import {Examen} from '../models/exam.model';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-supervisor-upload',
  templateUrl: './supervisor-upload.component.html',
  styleUrls: ['./supervisor-upload.component.css']
})
export class SupervisorUploadComponent implements OnInit {

  @Input() examens: Examen[] = [];
  examen_bestand: File;
  examen_naam: string;
  supervisorUploadForm;

  constructor(private serv: ExamService) {  }

  onSubmit() {
    this.serv.createExam(this.examen_naam, this.examen_bestand).subscribe(() => {
      this.serv.getExams().subscribe(data => this.examens = data);
    });
    const message = document.getElementsByClassName('alert alert-success') as HTMLCollectionOf<HTMLElement>;
    message[0].style.visibility = 'visible';

  }

  fileChanged(e) {
        this.examen_bestand = e.target.files[0];
    }


  ngOnInit() {
    this.supervisorUploadForm = new FormGroup({
                  vakNaam: new FormControl('', Validators.required),
                  bestand: new FormControl('', Validators.required)
                });

    this.serv.getExams().subscribe(data => this.examens = data);
    this.serv.getExamById(2);

  }

  getExamSkelet(id: number) {
    this.serv.getExamSkeletById(id).subscribe((file) => {
      this.serv.getExamById(id).subscribe((exam) => saveAs(file, exam.skelet));
    });
  }

}
