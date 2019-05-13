import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentDownloadComponent } from './student-download.component';

describe('StudentDownloadComponent', () => {
  let component: StudentDownloadComponent;
  let fixture: ComponentFixture<StudentDownloadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StudentDownloadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentDownloadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
