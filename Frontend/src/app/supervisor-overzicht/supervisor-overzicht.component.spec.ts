import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SupervisorOverzichtComponent } from './supervisor-overzicht.component';

describe('SupervisorOverzichtComponent', () => {
  let component: SupervisorOverzichtComponent;
  let fixture: ComponentFixture<SupervisorOverzichtComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SupervisorOverzichtComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SupervisorOverzichtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
