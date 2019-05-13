import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {User} from '../models/user';

const APIURL = 'http://localhost:8080/user/';

@Injectable()
export class AuthService {
  constructor(private http: HttpClient, private router: Router) { }

  setLoggedInUserId(id, role) {
    localStorage.setItem('loggedInUserId', id);
    localStorage.setItem('loggedInUserRole', role);
    if (this.getUserRole() === 'Student') {
      this.router.navigate(['/student']);
    } else {
      this.router.navigate(['/supervisor']);
    }
  }

  getLoggedInUserId() {
    return localStorage.getItem('loggedInUserId');
  }

  getUserById(id): Observable<User> {
    return this.http.get<User>(APIURL + id);
  }

  getLoggedInUser(): Observable<User> {
    const userId = localStorage.getItem('loggedInUserId');
    return this.getUserById(userId);
  }

  getUserRole() {
    return localStorage.getItem('loggedInUserRole');
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }


}
