
export enum RoleType {
  USER = "USER",
  ADMIN = "ADMIN"
}
export interface User {
  id: number;
  username: string;
  email: string;
  password: string;
  role: RoleType;
}
