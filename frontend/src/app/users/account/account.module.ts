import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AccountRoutingModule} from './account-routing.module';
import {AccountComponent} from './account/account.component';
import {AccountEditComponent} from './account/account-edit/account-edit.component';
import {PasswordChangeComponent} from './account/password-change/password-change.component';
import {DeleteAccountComponent} from './account/delete-account/delete-account.component';
import {ReactiveFormsModule} from '@angular/forms';



@NgModule({
  declarations: [
    AccountComponent,
    AccountEditComponent,
    PasswordChangeComponent,
    DeleteAccountComponent
  ],
  imports: [
    CommonModule,
    AccountRoutingModule,
    ReactiveFormsModule
  ]
})
export class AccountModule { }
