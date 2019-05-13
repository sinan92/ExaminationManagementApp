import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SupervisorExamenComponent } from './supervisor-examen.component';

describe('SupervisorExamenComponent', () => {
  let component: SupervisorExamenComponent;
  let fixture: ComponentFixture<SupervisorExamenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SupervisorExamenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SupervisorExamenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
