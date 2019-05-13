import {Component, Input, OnInit} from '@angular/core';
import {ExamService} from '../services/exams.service';
import {Examen} from '../models/exam.model';
import {FinishedExamService} from "../services/finished-exam.service";
import {AuthService} from "../services/auth.service";
import { FormGroup, FormControl, Validators,ReactiveFormsModule } from '@angular/forms';
import { FinishedExam } from '../models/finished-exam';

@Component({
    selector: 'app-student-upload',
    templateUrl: './student-upload.component.html',
    styleUrls: ['./student-upload.component.css']
})
export class StudentUploadComponent implements OnInit {
    @Input() examensStudent: Examen[] = [];
    examen_id: number;
    examen_bestand: File;
    finishedExam_hash: string;
    studentUploadForm;

    constructor(private serv: ExamService, private finishedServ: FinishedExamService, private auth: AuthService) {
    }

    onSubmit() {
        this.finishedServ.createFinishedExam(this.auth.getLoggedInUserId(), this.examen_id.toString(), this.examen_bestand).subscribe(() => {
            this.finishedServ.getExamObjectByUserId(this.auth.getLoggedInUserId()).subscribe(data =>
                {
                    this.finishedExam_hash = data.hash;
                    console.log(this.finishedExam_hash);
                });
        }
    );
        
        
        

            const message = document.getElementsByClassName('alert alert-success') as HTMLCollectionOf<HTMLElement>;
            message[0].style.visibility = 'visible';
            const messageMD5 = document.getElementsByClassName('alert alert-info') as HTMLCollectionOf<HTMLElement>;
            messageMD5[0].style.visibility = 'visible';
           
        }

    fileChanged(e) {
        this.examen_bestand = e.target.files[0];
    }

    ngOnInit() {
        this.studentUploadForm = new FormGroup({
            examInputFile: new FormControl('', Validators.compose([
                Validators.required,

              ])),

            examInput: new FormControl('', Validators.required)

        });

        this.studentUploadForm = new FormGroup({
            examInputFile: new FormControl('', Validators.compose([
                Validators.required,

              ])),

            examInput: new FormControl('', Validators.required)
        });

        this.serv.getExams().subscribe(data => {
          this.examensStudent = data;

          this.examen_id = this.examensStudent[0].id;
        });
    }



}
