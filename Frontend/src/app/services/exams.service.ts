import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Examen} from '../models/exam.model';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/Rx/';

const APIURL = 'http://localhost:8080/skelet/exam';

@Injectable()
export class ExamService {
  examens: Examen[] = [];

  constructor(private http: HttpClient) {
  }

  getExams(): Observable<Examen[]> {
    return this.http.get<Examen[]>(APIURL + '/all');
  }

  getExamById(id): Observable<Examen> {
    return this.http.get<Examen>(APIURL + '/asobject/' + id);
  }

  getExamSkeletById(id) {
    return this.http.get(APIURL + '/' + id, {responseType: 'blob'});
  }

  createExam(name: string, file: File) {
    const fd = new FormData();
    console.log(file.name);
    fd.append('naam', name);
    fd.append('file', file);
    return this.http.post(APIURL + '/create', fd);
  }

  updateExamById(id, exam: Examen, file: File) {
    const fd = new FormData();
    console.log(exam.skelet);
    fd.append('name', exam.naam);
    fd.append('file', file);
    return this.http.put<Examen>(APIURL + '/update/' + id, fd);
  }

  deleteExamById(id): Observable<HttpResponse<Response>> {
    return this.http.delete<Response>(APIURL + '/delete/' + id, {observe: 'response'});
  }
}
