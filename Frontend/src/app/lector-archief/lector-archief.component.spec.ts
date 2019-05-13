import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LectorArchiefComponent } from './lector-archief.component';

describe('LectorArchiefComponent', () => {
  let component: LectorArchiefComponent;
  let fixture: ComponentFixture<LectorArchiefComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LectorArchiefComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LectorArchiefComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
