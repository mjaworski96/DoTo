export interface LoggedUser {
  username: string;
  email: string;
  roles: UserRole[];
}
export interface UserRole {
  name: string;
}
export interface LoginDetails {
  username: string;
  password: string;
}
