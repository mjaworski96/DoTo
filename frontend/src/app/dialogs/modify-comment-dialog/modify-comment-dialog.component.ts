import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {CommentWithId} from '../../models/comment';

@Component({
  selector: 'app-modify-comment-dialog',
  templateUrl: './modify-comment-dialog.component.html',
  styleUrls: ['./modify-comment-dialog.component.css']
})
export class ModifyCommentDialogComponent implements OnInit {

  @Input()
  comment: CommentWithId

  constructor(private activeModal: NgbActiveModal) { }

  ngOnInit() {
  }

  closeWindow(comment: CommentWithId) {
    this.activeModal.close(comment);
  }
  
  dismiss() {
    this.activeModal.dismiss();
  }
}
