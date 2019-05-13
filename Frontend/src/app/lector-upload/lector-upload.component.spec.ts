import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LectorUploadComponent } from './lector-upload.component';

describe('LectorUploadComponent', () => {
  let component: LectorUploadComponent;
  let fixture: ComponentFixture<LectorUploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LectorUploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LectorUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
