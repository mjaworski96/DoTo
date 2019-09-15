import {Route, RouterModule} from '@angular/router';
import {NgModule} from '@angular/core';
import {LoggedUserGuard} from '../../guards/logged-user-guard';
import {AccountComponent} from './account/account.component';
import {UserResolveService} from './account/services/user-resolve.service';

const ACCOUNT_ROUTES: Route[] = [
  {
    path: 'account',
    component: AccountComponent as any,
    resolve: {
      user: UserResolveService
    },
    canActivate: [LoggedUserGuard],
    runGuardsAndResolvers: 'always'
  },
];

@NgModule({
  imports: [
    RouterModule.forChild(ACCOUNT_ROUTES),
  ],
  exports: [
    RouterModule
  ]
})
export class AccountRoutingModule {}
