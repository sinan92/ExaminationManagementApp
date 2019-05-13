import {Component, Input, OnInit} from '@angular/core';
import {ExamService} from '../services/exams.service';
import {Examen} from '../models/exam.model';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-supervisor-examen-aanpassen',
  templateUrl: './supervisor-examen-aanpassen.component.html',
  styleUrls: ['./supervisor-examen-aanpassen.component.css']
})
export class SupervisorExamenAanpassenComponent implements OnInit {
  @Input() examen: Examen = new Examen(null, '', '', null);
  id: number;
  naam: string;
  file: File;

  constructor(private serv: ExamService, private route: ActivatedRoute) { }

  onSubmit(form) {
    this.examen.naam = this.naam;
    this.serv.updateExamById(this.id, this.examen, this.file).subscribe();
  }

  fileChanged(e) {
    this.file = e.target.files[0];
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.id = params.id);
    this.serv.getExamById(this.id).subscribe((data) => this.examen = data);
  }

}
