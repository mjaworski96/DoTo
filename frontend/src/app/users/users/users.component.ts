import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {User} from '../../models/user';
import {Page} from '../../models/page';
import {UsersService} from '../services/users.service';
import {GlobalVariables} from '../../utils/global-variables';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  usersPage: Page<User>;

  pageSize = GlobalVariables.usersPageSize;

  constructor(private route: ActivatedRoute,
              private usersService: UsersService) { }

  ngOnInit() {
    this.usersPage = this.route.snapshot.data.users;
  }
  hasRole(user: User, role: string): boolean {
    for (let i = 0; i < user.roles.length; i++) {
      if (user.roles[i].name === role) {
        return true;
      }
    }
    return false;
  }
  changeRole(user: User, roleName: string): void {
    let operation: 'add' | 'remove';
    if (this.hasRole(user, roleName)) {
      operation = 'remove';
    } else {
      operation = 'add';
    }
    this.usersService.changeRole(
      user.id, {
        roles: [
          {
            roleName,
            operation
          }
        ]
      }
    ).toPromise().then(result => {
      user.roles = result.roles;
    });
  }
  updatePage(pageNumber: any): void {
    this.usersService.get(
      pageNumber - 1, this.pageSize
    ).toPromise()
      .then(page => this.usersPage = page);
  }
}
