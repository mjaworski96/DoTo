import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyProjectDialogComponent } from './modify-project-dialog.component';

describe('ModifyProjectDialogComponent', () => {
  let component: ModifyProjectDialogComponent;
  let fixture: ComponentFixture<ModifyProjectDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModifyProjectDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModifyProjectDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
