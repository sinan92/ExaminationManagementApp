import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ExamService} from '../services/exams.service';
import {Examen} from '../models/exam.model';

@Component({
  template: ''
})
export class SupervisorExamenVerwijderenComponent implements OnInit {
  id: number;
  delete: Boolean;
  examen: Examen;

  constructor(private serv: ExamService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    // Examen id ophalen
    this.route.params.subscribe(params => this.id = params.id);
    this.serv.getExamById(this.id).subscribe((exam) => this.examen = exam)
    this.delete = confirm('Weet u zeker dat u deze examen wilt verwijderen?');
    if (this.delete) { // Verwijderen
      this.serv.deleteExamById(this.id).subscribe((response: any) => {
        console.log(response);
        // Gelukt
        if (response.statusText === 'OK') {
          alert('Examen met naam: "' + this.examen.naam + '" is verwijdert.');
          this.router.navigate(['/supervisor/upload']);
        } else if ( response.statusText === 'NOT_FOUND') { // User niet gevonden
          // Geef melding en stuur terug
          alert('Examen niet gevonden');
          this.router.navigate(['/supervisor/upload']);
        }
        this.router.navigate(['/supervisor/upload']);
      });
    } else { // Niet verwijderen
      this.router.navigate(['/supervisor/upload']);
    }
  }

}
