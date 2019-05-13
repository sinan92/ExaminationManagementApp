import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LectorHeaderComponent } from './lector-header.component';

describe('LectorHeaderComponent', () => {
  let component: LectorHeaderComponent;
  let fixture: ComponentFixture<LectorHeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LectorHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LectorHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
