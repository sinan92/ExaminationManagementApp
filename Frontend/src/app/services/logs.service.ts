import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Log} from '../models/log.model';
import {HttpClient} from '@angular/common/http';
import 'rxjs/add/operator/map';

const APIURL = 'http://localhost:8080/logs/all';

@Injectable()
export class LogService {
  logs: Log[] = [];

  constructor(private http: HttpClient) {}

  getLogs(): Observable<Log[]> {
    return this.http.get<Log[]>(APIURL);
  }
}
