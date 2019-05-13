import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SupervisorExamenAanpassenComponent } from './supervisor-examen-aanpassen.component';

describe('SupervisorExamenAanpassenComponent', () => {
  let component: SupervisorExamenAanpassenComponent;
  let fixture: ComponentFixture<SupervisorExamenAanpassenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SupervisorExamenAanpassenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SupervisorExamenAanpassenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
