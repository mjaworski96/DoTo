import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AuthenticationModule} from './authentication/authentication.module';
import {AccountModule} from './account/account.module';



@NgModule({
  declarations: [
  ],
  imports: [
    CommonModule,
    AuthenticationModule,
    AccountModule
  ]
})
export class UsersModule { }
