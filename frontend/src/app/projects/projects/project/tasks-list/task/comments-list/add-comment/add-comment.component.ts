import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {GlobalVariables} from "../../../../../../../utils/global-variables";
import {SessionStorageService} from "../../../../../../../shared/services/session-storage.service";
import {ProjectsService} from "../../../../../../services/projects.service";
import {Router} from "@angular/router";
import {CommentsService} from "../../../../../../services/comments.service";
import {TaskWithId} from "../../../../../../../models/task";
import {CommentWithId} from "../../../../../../../models/comment";

@Component({
  selector: 'app-add-comment',
  templateUrl: './add-comment.component.html',
  styleUrls: ['./add-comment.component.css']
})
export class AddCommentComponent implements OnInit {

  @Input()
  taskId: number;

  @Input()
  comment: CommentWithId;

  @Output()
  newComment = new EventEmitter<CommentWithId>();

  addCommentForm: FormGroup;

  minContentLength = GlobalVariables.minCommentContentLength;
  maxContentLength = GlobalVariables.maxCommentContentLength;

  constructor(private formBuilder: FormBuilder,
              private sessionStorageService: SessionStorageService,
              private commentsService: CommentsService,
              private router: Router) { }

  ngOnInit() {
    this.buildForm();
  }
  buildForm(): void {
    this.addCommentForm = this.formBuilder.group({
      content: ['', [
        Validators.required,
        Validators.minLength(this.minContentLength),
        Validators.maxLength(this.maxContentLength)
      ]]
    });
    this.setDefaultValues();
  }
  setDefaultValues(): void {
    if (this.comment !== undefined) {
      this.addCommentForm.controls.content.setValue(this.comment.content);
    }
  }
  edit() {
    if (this.comment  === undefined) {
      this.add();
    } else {
      this.modify();
    }
  }
  add() {
    this.commentsService.create(
      this.taskId,
      this.addCommentForm.value
    ).toPromise()
      .then(result => {
        this.newComment.emit(result);
      });
  }
  modify() {
    this.commentsService.update(
      this.comment.id,
      this.addCommentForm.value
    ).toPromise()
      .then(result => {
        this.newComment.emit(result);
      });
  }
}
