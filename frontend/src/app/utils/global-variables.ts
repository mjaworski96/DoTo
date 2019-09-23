export class GlobalVariables {
  static minProjectNameLength = 1;
  static maxProjectNameLength = 50;
  static maxProjectDescriptionLength = 300;

  static minTaskShortDescriptionLength = 1;
  static maxTaskShortDescriptionLength = 100;
  static maxTaskFullDescriptionLength = 1000;

  static minCommentContentLength = 1;
  static maxContentLength = 50;

  static usersPageSize = 10;
  static projectsPageSize = 15;

  static usersApi = '/api/users';
  static projectsApi = '/api/projects';
  static tasksApi = '/api/tasks';
  static commentsApi = '/api/comments';

  static projectApiPostfix = 'projects';
  static tasksApiPostfix = 'tasks';
  static stateApiPostfix = 'state';
  static commentsApiPostfix = 'comments';
}
