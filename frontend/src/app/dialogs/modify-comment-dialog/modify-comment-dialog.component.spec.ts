import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyCommentDialogComponent } from './modify-comment-dialog.component';

describe('ModifyCommentDialogComponent', () => {
  let component: ModifyCommentDialogComponent;
  let fixture: ComponentFixture<ModifyCommentDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModifyCommentDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModifyCommentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
