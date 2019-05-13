import {Examen} from './exam.model';

export class FinishedExam {
  id: number;
  exam: Examen;
  finishedExam: string;
  hash: string;

  constructor(
    id: number,
    exam: Examen,
    finishedExam: string,
    hash: string
  ) {
    this.id = id;
    this.exam = exam;
    this.finishedExam = finishedExam;
    this.hash = hash;
  }
}
