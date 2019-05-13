import { TestBed, inject } from '@angular/core/testing';

import { ExamService } from './exams.service';

describe('ExamService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ExamService]
    });
  });

  it('should be created', inject([ExamService], (service: ExamService) => {
    expect(service).toBeTruthy();
  }));
});
