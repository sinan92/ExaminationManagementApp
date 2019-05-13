import { Injectable } from '@angular/core';
import {FinishedExam} from '../models/finished-exam';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';


const APIURL = 'http://localhost:8080/finished/exam/';

@Injectable()
export class FinishedExamService {
  finishedExamens: FinishedExam[] = [];

  constructor(private http: HttpClient) {}

  getExams(): Observable<FinishedExam[]> {
    return this.http.get<FinishedExam[]>(APIURL + '/all');
  }

  getExamById(id): Observable<FinishedExam> {
    return this.http.get<FinishedExam>(APIURL + id);
  }

  getExamObjectByUserId(id): Observable<FinishedExam> {
    return this.http.get<FinishedExam>(APIURL + 'object/' +id);
  }

  getFinishedExamFileById(id) {
    return this.http.get(APIURL + id, {responseType: 'blob'});
  }

  getAllFinishedExamsFile() {
    return this.http.get(APIURL + 'zip/all', {responseType: 'blob'});
  }

  createFinishedExam(user_id: string, exam_id: string, file: File) {
    const fd = new FormData();
    console.log(file.name);
    fd.append('user_id', user_id);
    fd.append('exam_id', exam_id);
    fd.append('file', file);
    return this.http.post(APIURL + 'create', fd);
  }

  deleteExamById(id): Observable<Response> {
    return this.http.delete<Response>(APIURL + 'delete/' + id);
  }
}
