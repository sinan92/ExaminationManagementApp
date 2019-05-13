import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SupervisorUploadComponent } from './supervisor-upload.component';

describe('SupervisorUploadComponent', () => {
  let component: SupervisorUploadComponent;
  let fixture: ComponentFixture<SupervisorUploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SupervisorUploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SupervisorUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
