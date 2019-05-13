import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SupervisorHeaderComponent } from './supervisor-header.component';

describe('SupervisorHeaderComponent', () => {
  let component: SupervisorHeaderComponent;
  let fixture: ComponentFixture<SupervisorHeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SupervisorHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SupervisorHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
