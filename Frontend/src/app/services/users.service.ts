import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';
import {User} from '../models/user';

const APIURL = 'http://localhost:8080/user/';

@Injectable()
export class UsersService {

  users: User[] = [];

  constructor(private http: HttpClient) {}

  getStudenten(): Observable<User[]> {
    return this.http.get<User[]>(APIURL + 'all/student');
  }

  getUserById(id): Observable<User> {
    return this.http.get<User>(APIURL + id);
  }

  getUserByName(name): Observable<User> {
    return this.http.get<User>(APIURL + 'name/' + name);
  }

  getUserByNumber(number): Observable<User> {
    return this.http.get<User>(APIURL + 'number/' + number);
  }

  createUser(user: User): Observable<Response> {
    return this.http.post<Response>(APIURL + 'add', user);
  }

}
