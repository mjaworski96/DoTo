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
export interface RegisterUserDetails {
  username: string;
  password: string;
  email: string;
}
export interface ResetPassword {
  email: string;
}
export interface User {
  username: string;
  email: string;
  roles: UserRole[];
}
export interface UserUpdateData {
  username: string;
  email: string;
}
export interface PasswordChange {
  oldPassword: string;
  newPassword: string;
}
export interface Roles {
  roles: UserRole[];
}
export interface RoleChange {
  operation: 'add' | 'remove';
  roleName: string;
}
export interface RolesChanges {
  roles: RoleChange[];
}
