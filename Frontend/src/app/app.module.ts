import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {appRoutes} from './app.routes';
import {HttpClientModule} from '@angular/common/http';


import {AppComponent} from './app.component';
import {LoginScreenComponent} from './login-screen/login-screen.component';
import {StudentDownloadComponent} from './student-download/student-download.component';
import {StudentUploadComponent} from './student-upload/student-upload.component';
import {AngularFontAwesomeModule, AngularFontAwesomeService} from 'angular-font-awesome';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {SupervisorExamenComponent} from './supervisor-examen/supervisor-examen.component';
import {SupervisorLogsComponent} from './supervisor-logs/supervisor-logs.component';
import {SupervisorOverzichtComponent} from './supervisor-overzicht/supervisor-overzicht.component';
import {UitloggenComponent} from './uitloggen/uitloggen.component';
import {LectorDownloadComponent} from './lector-download/lector-download.component';
import {LectorUploadComponent} from './lector-upload/lector-upload.component';
import {LectorArchiefComponent} from './lector-archief/lector-archief.component';
import {StudentHeaderComponent} from './student-header/student-header.component';
import {SupervisorHeaderComponent} from './supervisor-header/supervisor-header.component';
import {LectorHeaderComponent} from './lector-header/lector-header.component';
import {IngeleverdPipe} from './pipes/ingeleverd.pipe';
import {FormsModule} from '@angular/forms';
import { SupervisorUploadComponent } from './supervisor-upload/supervisor-upload.component';
import { HashAanwezigPipe } from './pipes/hash-aanwezig.pipe';
import {AuthService} from './services/auth.service';
import {StudentGuard} from './guards/student.guard';
import {SupervisorGuard} from './guards/supervisor.guard';
import { LogoutComponent } from './logout/logout.component';
import {LogService} from './services/logs.service';
import {SupervisorExamenAanpassenComponent} from './supervisor-examen-aanpassen/supervisor-examen-aanpassen.component';
import {FinishedExamService} from './services/finished-exam.service';
import {LoginGuard} from './guards/login.guard';
import { FormGroup, FormControl, Validators,ReactiveFormsModule } from '@angular/forms';
import { SupervisorExamenVerwijderenComponent } from './supervisor-examen-verwijderen/supervisor-examen-verwijderen.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginScreenComponent,
    StudentDownloadComponent,
    StudentUploadComponent,
    SupervisorExamenComponent,
    SupervisorLogsComponent,
    SupervisorOverzichtComponent,
    UitloggenComponent,
    LectorDownloadComponent,
    LectorUploadComponent,
    LectorArchiefComponent,
    StudentHeaderComponent,
    SupervisorHeaderComponent,
    LectorHeaderComponent,
    IngeleverdPipe,
    SupervisorUploadComponent,
    HashAanwezigPipe,
    LogoutComponent,
    SupervisorExamenAanpassenComponent,
    SupervisorExamenVerwijderenComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    AngularFontAwesomeModule,
    NgbModule.forRoot(),
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [AuthService, StudentGuard, SupervisorGuard, LoginGuard,
        LogService,
        FinishedExamService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
