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
  description: string;
}
export interface UserUpdateData {
  username: string;
  email: string;
  description: string;
}
