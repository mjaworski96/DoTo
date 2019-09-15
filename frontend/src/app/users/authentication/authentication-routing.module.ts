import {Route, RouterModule} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {NgModule} from '@angular/core';
import {NotLoggedUserGuard} from '../../guards/not-logged-user-quard';

const AUTHENTICATION_ROUTES: Route[] = [
  {
    path: 'login',
    component: LoginComponent as any,
    canActivate: [NotLoggedUserGuard],
    runGuardsAndResolvers: 'always'
  }
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
