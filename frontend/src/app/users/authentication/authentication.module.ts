import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import {AuthenticationRoutingModule} from './authentication-routing.module';
import {ReactiveFormsModule} from '@angular/forms';
import { RegisterComponent } from './register/register.component';
import { ResetPasswordComponent } from './login/reset-password/reset-password.component';



@NgModule({
  declarations: [LoginComponent, RegisterComponent, ResetPasswordComponent],
  imports: [
    CommonModule,
    AuthenticationRoutingModule,
    ReactiveFormsModule
  ]
})
export class AuthenticationModule { }
