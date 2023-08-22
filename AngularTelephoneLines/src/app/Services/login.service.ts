import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";
import jwtDecode from "jwt-decode";
import {User} from "../Models/User";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  userData: User | any;

  isAuthenticated: boolean = false;
  accessToken!: any;

//:{headers: HttpHeaders}  :HttpParams
  constructor(private http: HttpClient) {
  }

  public login(username: string, password: string):Observable<any> {
    let options: {headers: HttpHeaders} = {
      headers: new HttpHeaders().set("Content-Type", "application/x-www-form-urlencoded")
    }

    let params: HttpParams = new HttpParams().set("username", username).set("password", password);
    return this.http.post(environment.backEndHost + "/auth/login", params, options);
  }

  loadProfile(data: any):void {
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
  logout():void {
    this.isAuthenticated = false;
    this.accessToken = undefined;
    this.userData = undefined;
  }

  public getUserData(): User {
    return this.userData;
  }
  setUserData(user: User):void {
    this.userData = {
      id: user.id,
      username: user.username,
      email: user.email,
      role: user.role
    };
  }


}
