import { Component, OnInit } from '@angular/core';
import {AuthService} from '../services/auth.service';
import {UsersService} from '../services/users.service';
import {User} from '../models/user';
import { FormGroup, FormControl, Validators,ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-login-screen',
  templateUrl: './login-screen.component.html',
  styleUrls: ['./login-screen.component.css']
})
export class LoginScreenComponent implements OnInit {
  studentNumber: string;
  firstName: string;
  lastName: string;
  newStudent: User = new User('', '', '');
  loginForm;

  constructor(private auth: AuthService, private user: UsersService ) { }

  ngOnInit() {
    this.loginForm = new FormGroup({
              studentnummer: new FormControl('', Validators.compose([
              Validators.required,
              Validators.maxLength(8),
              Validators.minLength(8),
              Validators.pattern('^[0-9]*$')
            ])),
            naam: new FormControl('', Validators.required),
            achternaam: new FormControl('', Validators.required)
          });

          console.log(this.auth.getUserRole());
  }

  onSubmit() {
    this.user.getUserByNumber(this.studentNumber).subscribe((student) => {

      console.log('Test3');
      // Inloggen als student bestaat
      if (student != null && student.firstName === this.firstName && student.lastName === this.lastName) {
        console.log('Test')
        this.user.getUserByNumber(student.studentNumber).subscribe((existingStudent) => {
          this.auth.setLoggedInUserId(existingStudent.id, existingStudent.role);
          console.log(existingStudent.role);
        });

      } else { // Student bestaat niet, maak nieuwe student aan en login
        this.newStudent.studentNumber = this.studentNumber;
        this.newStudent.firstName = this.firstName;
        this.newStudent.lastName = this.lastName;
        this.user.createUser(this.newStudent).subscribe((result: any) => {
          if (result.statusCode === 'CREATED') {
            console.log('Test2')
            // Gecreeerde student ophalen om vervolgens de student met id op te halen
            this.user.getUserByNumber(this.newStudent.studentNumber).subscribe((createdStudent) => {
              this.auth.setLoggedInUserId(createdStudent.id, createdStudent.role);
              console.log('Test2');
            });
          }
        });
      }
    });
  }

}
