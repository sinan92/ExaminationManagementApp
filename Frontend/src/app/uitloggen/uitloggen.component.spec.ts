import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UitloggenComponent } from './uitloggen.component';

describe('UitloggenComponent', () => {
  let component: UitloggenComponent;
  let fixture: ComponentFixture<UitloggenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UitloggenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UitloggenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
