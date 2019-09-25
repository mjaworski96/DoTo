import {Component, Input, OnInit} from '@angular/core';
import {CommentsService} from '../../../../../services/comments.service';
import {Comments, CommentWithId} from "../../../../../../models/comment";
import {TaskWithId} from "../../../../../../models/task";
import {DeleteProjectDialogComponent} from "../../../../../../dialogs/delete-project-dialog/delete-project-dialog.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-comments-list',
  templateUrl: './comments-list.component.html',
  styleUrls: ['./comments-list.component.css']
})
export class CommentsListComponent implements OnInit {

  comments: Comments;

  @Input()
  taskId: number;

  constructor(private commentsService: CommentsService,
              private modalService: NgbModal) { }

  ngOnInit() {
    this.commentsService.getForTask(this.taskId)
      .toPromise()
      .then(result => {
        this.comments = result;
      });
  }
  addComment(comment: CommentWithId) {
    this.comments.comments.push(comment);
  }
  delete(comment: CommentWithId): void {
    const modalRef = this.modalService.open(DeleteProjectDialogComponent);
    modalRef.result.then(res => {
      if (res === true) {
        this.commentsService.delete(
          comment.id
        ).toPromise().then(result => {
          this.commentsService.getForTask(this.taskId)
            .toPromise()
            .then(comments => {
              this.comments = comments;
            });
        });
      }
    }).catch(error => {
      // prevent error in console
    });
  }
}
