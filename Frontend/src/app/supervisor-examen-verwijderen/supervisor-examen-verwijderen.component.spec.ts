import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SupervisorExamenVerwijderenComponent } from './supervisor-examen-verwijderen.component';

describe('SupervisorExamenVerwijderenComponent', () => {
  let component: SupervisorExamenVerwijderenComponent;
  let fixture: ComponentFixture<SupervisorExamenVerwijderenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SupervisorExamenVerwijderenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SupervisorExamenVerwijderenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
