import {Component, Input, OnInit} from '@angular/core';
import {CommentsService} from '../../../../../services/comments.service';
import {Comments, CommentWithId} from "../../../../../../models/comment";
import {TaskWithId} from "../../../../../../models/task";

@Component({
  selector: 'app-comments-list',
  templateUrl: './comments-list.component.html',
  styleUrls: ['./comments-list.component.css']
})
export class CommentsListComponent implements OnInit {

  comments: Comments;

  @Input()
  taskId: number;

  constructor(private commentsService: CommentsService) { }

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
}
