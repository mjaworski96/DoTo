import {Route, RouterModule} from '@angular/router';
import {NgModule} from '@angular/core';
import {UsersComponent} from './users/users.component';
import {UsersResolveService} from './services/users-resolve.service';
import {AdminGuard} from '../guards/admin-guard';

const USERS_ROUTES: Route[] = [
  {
    path: 'users',
    component: UsersComponent as any,
    resolve: {
      users: UsersResolveService
    },
    canActivate: [AdminGuard],
    runGuardsAndResolvers: 'always'
  },
];

@NgModule({
  imports: [
    RouterModule.forChild(USERS_ROUTES),
  ],
  exports: [
    RouterModule
  ]
})
export class UsersRoutingModule {}
