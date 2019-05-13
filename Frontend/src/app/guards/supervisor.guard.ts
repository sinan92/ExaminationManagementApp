import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import { Observable } from 'rxjs/Observable';
import {AuthService} from '../services/auth.service';

@Injectable()
export class SupervisorGuard implements CanActivate {
  constructor(private supervisor: AuthService, private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (this.supervisor.getLoggedInUserId() !== null && this.supervisor.getUserRole() === 'Supervisor') {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
