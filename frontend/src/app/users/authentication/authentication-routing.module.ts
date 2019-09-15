import {Route, RouterModule} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {NgModule} from '@angular/core';
import {NotLoggedUserGuard} from '../../guards/not-logged-user-quard';
import {RegisterComponent} from './register/register.component';
import {ResetPasswordComponent} from './login/reset-password/reset-password.component';

const AUTHENTICATION_ROUTES: Route[] = [
  {
    path: 'login',
    component: LoginComponent as any,
    canActivate: [NotLoggedUserGuard],
    runGuardsAndResolvers: 'always'
  },
  {
    path: 'register',
    component: RegisterComponent as any,
    canActivate: [NotLoggedUserGuard],
    runGuardsAndResolvers: 'always'
  },
  {
    path: 'reset-password',
    component: ResetPasswordComponent as any,
    canActivate: [NotLoggedUserGuard],
  },
];

@NgModule({
  imports: [
    RouterModule.forChild(AUTHENTICATION_ROUTES),
  ],
  exports: [
    RouterModule
  ]
})
export class AuthenticationRoutingModule {}
