import { Component, OnInit } from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {DeleteAccountDialogComponent} from '../../../../dialogs/delete-account-dialog/delete-account-dialog.component';
import {UserService} from '../../services/user.service';
import {SessionStorageService} from '../../../../shared/services/session-storage.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-delete-account',
  templateUrl: './delete-account.component.html',
  styleUrls: ['./delete-account.component.css']
})
export class DeleteAccountComponent implements OnInit {

  constructor(private modalService: NgbModal,
              private userService: UserService,
              private sessionStorageService: SessionStorageService,
              private router: Router
  ) { }

  ngOnInit() {
  }

  delete(): void {
    const modalRef = this.modalService.open(DeleteAccountDialogComponent);
    modalRef.result.then(res => {
      if (res === true) {
        this.userService.deleteAccount(
          this.sessionStorageService.getUsername()
        ).toPromise().then(result => {
          this.sessionStorageService.logout();
          this.router.navigate(['/']);
        });
      }
    });
  }
}
