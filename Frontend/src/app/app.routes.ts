import {Routes} from '@angular/router';
import {LoginScreenComponent} from './login-screen/login-screen.component';
import {StudentDownloadComponent} from './student-download/student-download.component';
import {StudentUploadComponent} from './student-upload/student-upload.component';
import {SupervisorLogsComponent} from './supervisor-logs/supervisor-logs.component';
import {SupervisorOverzichtComponent} from './supervisor-overzicht/supervisor-overzicht.component';
import {SupervisorUploadComponent} from './supervisor-upload/supervisor-upload.component';
import {StudentGuard} from './guards/student.guard';
import {SupervisorGuard} from './guards/supervisor.guard';
import {LogoutComponent} from './logout/logout.component';
import {SupervisorExamenAanpassenComponent} from './supervisor-examen-aanpassen/supervisor-examen-aanpassen.component';
import {LoginGuard} from './guards/login.guard';
import {SupervisorExamenVerwijderenComponent} from './supervisor-examen-verwijderen/supervisor-examen-verwijderen.component';

export const appRoutes: Routes = [
  {path: '', component: LoginScreenComponent, canActivate: [LoginGuard]},
  {path: 'login', component: LoginScreenComponent, canActivate: [LoginGuard]},
  {path: 'student', component: StudentDownloadComponent, canActivate: [StudentGuard]},
  {path: 'student/download', component: StudentDownloadComponent, canActivate: [StudentGuard]},
  {path: 'student/upload', component: StudentUploadComponent, canActivate: [StudentGuard]},
  {path: 'supervisor', component: SupervisorUploadComponent, canActivate: [SupervisorGuard]},
  {path: 'supervisor/upload', component: SupervisorUploadComponent, canActivate: [SupervisorGuard]},
  {path: 'supervisor/logs', component: SupervisorLogsComponent, canActivate: [SupervisorGuard]},
  {path: 'supervisor/overzicht', component: SupervisorOverzichtComponent, canActivate: [SupervisorGuard]},
  {path: 'uitloggen', component: LogoutComponent},
  {path: 'supervisor/examen/aanpassen/:id', component: SupervisorExamenAanpassenComponent, canActivate: [SupervisorGuard]},
  {path: 'supervisor/examen/verwijderen/:id', component: SupervisorExamenVerwijderenComponent, canActivate: [SupervisorGuard]}
  ];

