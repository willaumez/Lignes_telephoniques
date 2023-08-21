import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";
import jwtDecode from "jwt-decode";
import {User} from "../Models/User";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  userData: User | any;

  isAuthenticated: boolean = false;
  accessToken!: any;

  constructor(private http: HttpClient) {
  }

  public login(username: string, password: string) {
    let options = {
      headers: new HttpHeaders().set("Content-Type", "application/x-www-form-urlencoded")
    }

    let params = new HttpParams().set("username", username).set("password", password);
    return this.http.post(environment.backEndHost + "/auth/login", params, options);
  }

  loadProfile(data: any) {
    this.isAuthenticated = true;
    this.accessToken = data['access-token'];
    let decodeJwt: any = jwtDecode(this.accessToken);
    this.userData = {
      id: decodeJwt.id,
      username: decodeJwt.username,
      email: decodeJwt.email,
      password: decodeJwt.password,
      role: decodeJwt.role,
    };
  }
  logout() {
    this.isAuthenticated = false;
    this.accessToken = undefined;
    this.userData = undefined;
  }

  public getUserData(): User {
    return this.userData;
  }
  setUserData(user: User) {
    this.userData = {
      id: user.id,
      username: user.username,
      email: user.email,
    };
  }

}
