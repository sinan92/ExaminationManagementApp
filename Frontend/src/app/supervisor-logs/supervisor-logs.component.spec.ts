import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SupervisorLogsComponent } from './supervisor-logs.component';

describe('SupervisorLogsComponent', () => {
  let component: SupervisorLogsComponent;
  let fixture: ComponentFixture<SupervisorLogsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SupervisorLogsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SupervisorLogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
