import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {LoggedUser, User} from '../../../../models/user';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ErrorHandlingService} from '../../../../shared/services/error-handling.service';
import {SessionStorageService} from '../../../../shared/services/session-storage.service';
import {HttpResponse} from '@angular/common/http';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-account-edit',
  templateUrl: './account-edit.component.html',
  styleUrls: ['./account-edit.component.css']
})
export class AccountEditComponent implements OnInit {
  @ViewChild('accountModifiedMsg', {static: false})
  translatedMessage: ElementRef;

  user: User;
  userForm: FormGroup;

  minUsernameLength = 3;
  maxUsernameLength = 20;

  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              private route: ActivatedRoute,
              private errorHandlingService: ErrorHandlingService,
              private sessionStorageService: SessionStorageService,
              private toastr: ToastrService) { }

  ngOnInit(): void {
    this.user = this.route.snapshot.data.user;
    this.buildForm();
  }
  buildForm(): void {
    this.userForm = this.formBuilder.group({
      username: [this.user.username, [
        Validators.required,
        Validators.minLength(this.minUsernameLength),
        Validators.maxLength(this.maxUsernameLength)
      ]],
      email: [this.user.email, Validators.required],
    });
  }
  update(): void {
    this.userService.updateAccount(
      this.user.username,
      this.userForm.value
    ).toPromise().then((result: HttpResponse <LoggedUser>) => {
      this.sessionStorageService.storeSession(
        result.body,
        result.headers.get('Authorization')
      );
      this.toastr.info(this.translatedMessage.nativeElement.innerHTML, '', {
        timeOut: 5000,
        closeButton: true
      });
    });
  }

}
