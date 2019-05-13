import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import { Observable } from 'rxjs/Observable';
import {AuthService} from '../services/auth.service';

@Injectable()
export class StudentGuard implements CanActivate {
  constructor(private student: AuthService, private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (this.student.getLoggedInUserId() !== null && this.student.getUserRole() === 'Student') {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
