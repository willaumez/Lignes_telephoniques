import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";
import jwtDecode from "jwt-decode";
import {User} from "../Models/User";
import {Observable} from "rxjs";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  userData: User | any;

  isAuthenticated: boolean = false;
  accessToken!: any;

  constructor(private http: HttpClient, private router: Router) {
    this.accessToken = localStorage.getItem('access-token');
  }

  login(username: string, password: string): Observable<any> {
    let options: { headers: HttpHeaders } = {
      headers: new HttpHeaders().set("Content-Type", "application/x-www-form-urlencoded")
    }

    let params: HttpParams = new HttpParams().set("username", username).set("password", password);
    return this.http.post(environment.backEndHost + "/auth/login", params, options);
  }

  loadProfile(token: any): void {
    if (token) {
      let decodeJwt: any = jwtDecode(token);
      const expirationDate = new Date(decodeJwt.exp * 1000); // Convertir en millisecondes

      if (expirationDate > new Date()) { // Vérifier si le token est expiré
        this.accessToken = token;
        localStorage.setItem('access-token', this.accessToken);
        this.userData = {
          id: decodeJwt.id,
          username: decodeJwt.username,
          email: decodeJwt.email,
          password: decodeJwt.password,
          role: decodeJwt.role,
        };
        this.isAuthenticated = true;
      } else {
        console.error('Token has expired');
        this.logout(); // Vous pouvez appeler une méthode de déconnexion pour nettoyer
      }
    } else {
      console.error('Token is missing or invalid');
    }
  }

  logout(): void {
    this.isAuthenticated = false;
    this.accessToken = undefined;
    this.userData = undefined;
    localStorage.removeItem('access-token');
    this.router.navigateByUrl("/login");
  }

  public getUserData(): User {
    return this.userData;
  }
  setUserData(user: User): void {
    this.userData = {
      id: user.id,
      username: user.username,
      email: user.email,
      role: user.role
    };
  }


}
