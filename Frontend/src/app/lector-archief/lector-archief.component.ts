import {Component, Input, OnInit} from '@angular/core';
import {ExamenBundel} from '../models/exam-bundle';

@Component({
  selector: 'app-lector-archief',
  templateUrl: './lector-archief.component.html',
  styleUrls: ['./lector-archief.component.css']
})
export class LectorArchiefComponent implements OnInit {

  @Input() examenBundels: ExamenBundel[] = [
    new ExamenBundel('Java Advanced', Date.now()),
    new ExamenBundel('Java Advanced', Date.now()),
    new ExamenBundel('Java Advanced', Date.now()),
    new ExamenBundel('Java Advanced', Date.now()),
    new ExamenBundel('Java Advanced', Date.now()),
    new ExamenBundel('Java Advanced', Date.now()),
    new ExamenBundel('Java Advanced', Date.now()),
    new ExamenBundel('Java Advanced', Date.now()),
    new ExamenBundel('Java Advanced', Date.now()),
    new ExamenBundel('Java Advanced', Date.now()),
  ];

  constructor() { }

  ngOnInit() {
  }

}
