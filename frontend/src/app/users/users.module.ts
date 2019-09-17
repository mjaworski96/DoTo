import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AuthenticationModule} from './authentication/authentication.module';
import {AccountModule} from './account/account.module';
import { UsersComponent } from './users/users.component';
import {UsersRoutingModule} from './users-routing.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';



@NgModule({
  declarations: [
  UsersComponent
  ],
  imports: [
    CommonModule,
    AuthenticationModule,
    AccountModule,
    UsersRoutingModule,
    NgbModule
  ]
})
export class UsersModule { }
