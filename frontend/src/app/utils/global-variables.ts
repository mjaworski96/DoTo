import {IndividualConfig} from 'ngx-toastr';

export class GlobalVariables {
  static maxProjectNameLength = 50;
  static maxProjectDescriptionLength = 300;

  static maxTaskShortDescriptionLength = 100;
  static maxTaskFullDescriptionLength = 1000;

  static maxCommentContentLength = 500;

  static minUsernameLength = 3;
  static maxUsernameLength = 20;

  static minPasswordLength = 3;
  static maxPasswordLength = 20;

  static maxLabelNameLength = 50;

  static usersPageSize = 10;
  static projectsPageSize = 15;

  static textAreaRows = 3;

  static usersApi = '/api/users';
  static projectsApi = '/api/projects';
  static tasksApi = '/api/tasks';
  static commentsApi = '/api/comments';
  static labelsApi = '/api/labels'


  static projectApiPostfix = 'projects';
  static tasksApiPostfix = 'tasks';
  static stateApiPostfix = 'state';
  static commentsApiPostfix = 'comments';
  static archivedApiPostfix = 'archived';
  static labelApiPostfix = 'labels'

  static toastrConfig: Partial<IndividualConfig> = {
    timeOut: 5000,
    closeButton: true
  };
}
