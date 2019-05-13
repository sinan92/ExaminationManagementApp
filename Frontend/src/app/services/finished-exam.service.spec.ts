import { TestBed, inject } from '@angular/core/testing';

import { FinishedExamService } from './finished-exam.service';

describe('FinishedExamService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FinishedExamService]
    });
  });

  it('should be created', inject([FinishedExamService], (service: FinishedExamService) => {
    expect(service).toBeTruthy();
  }));
});
