import {FinishedExam} from './finished-exam';

export class User {
  id: number;
  role: string;
  firstName: string;
  lastName: string;
  studentNumber: string;
  status: boolean;
  finishedExam: FinishedExam;
  datum: number;

  constructor(
    firstName: string,
    lastName: string,
    studentNumber: string,
  ) {
    this.firstName = firstName;
    this.lastName = firstName;
    this.studentNumber = studentNumber;
  }
}
