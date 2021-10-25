import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {GlobalVariables} from '../../../../../../../utils/global-variables';
import {SessionStorageService} from '../../../../../../../shared/services/session-storage.service';
import {CommentsService} from '../../../../../../services/comments.service';
import {CommentWithId} from '../../../../../../../models/comment';
import {finalize} from "rxjs/operators";

@Component({
  selector: 'app-edit-comment',
  templateUrl: './edit-comment.component.html',
  styleUrls: ['./edit-comment.component.css']
})
export class EditCommentComponent implements OnInit {

  @Input()
  taskId: number;

  @Input()
  comment: CommentWithId;

  @Output()
  newComment = new EventEmitter<CommentWithId>();

  editCommentForm: FormGroup;

  maxContentLength = GlobalVariables.maxCommentContentLength;
  textAreaRows = GlobalVariables.textAreaRows;
  
  processing = false;

  constructor(private formBuilder: FormBuilder,
              private commentsService: CommentsService) { }

  ngOnInit() {
    this.buildForm();
  }
  buildForm(): void {
    this.editCommentForm = this.formBuilder.group({
      content: ['', [
        Validators.required,
        Validators.maxLength(this.maxContentLength)
      ]]
    });
    this.setDefaultValues();
  }
  setDefaultValues(): void {
    if (this.comment !== undefined) {
      this.editCommentForm.controls.content.setValue(this.comment.content);
    }
  }
  edit() {
    if (this.comment === undefined) {
      this.add();
    } else {
      this.modify();
    }
  }
  add() {
    this.processing = true;
    this.commentsService.create(
      this.taskId,
      this.editCommentForm.value
    ).pipe(
      finalize(() => this.processing = false))
      .toPromise()
      .then(result => {
        this.editCommentForm.reset();
        this.newComment.emit(result);
      });
  }
  modify() {
    this.processing = true;
    this.commentsService.update(
      this.comment.id,
      this.editCommentForm.value
    ).pipe(
      finalize( () => this.processing = false))
      .toPromise()
      .then(result => {
        this.newComment.emit(result);
      });
  }
}
