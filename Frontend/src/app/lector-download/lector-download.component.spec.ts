import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LectorDownloadComponent } from './lector-download.component';

describe('LectorDownloadComponent', () => {
  let component: LectorDownloadComponent;
  let fixture: ComponentFixture<LectorDownloadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LectorDownloadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LectorDownloadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
