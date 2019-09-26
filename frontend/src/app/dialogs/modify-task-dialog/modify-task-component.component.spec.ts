import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyTaskDialogComponent } from './modify-task-dialog.component';

describe('ModifyTaskDialogComponent', () => {
  let component: ModifyTaskDialogComponent;
  let fixture: ComponentFixture<ModifyTaskDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModifyTaskDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModifyTaskDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
