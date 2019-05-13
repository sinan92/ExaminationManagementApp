import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import { Observable } from 'rxjs/Observable';
import {AuthService} from '../services/auth.service';

@Injectable()
export class LoginGuard implements CanActivate {
  constructor(private user: AuthService, private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (this.user.getLoggedInUserId() === null) {
      return true;
    } else if (this.user.getUserRole() === 'Student') {
      this.router.navigate(['/student']);
      return false;
    } else {
      this.router.navigate(['/supervisor']);
      return false;
    }
  }
}
