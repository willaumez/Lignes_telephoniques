import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {User} from "../Models/User";
import {catchError, Observable, throwError} from "rxjs";
import {PagedResponse} from "../Models/PagedResponse";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {}

  /*  public listUsers():Observable<Array<User>> {
      return this.http.get<Array<User>>(environment.backEndHost+"/users");
    }*/

  /*public listUsers(page: number = 0, size: number = 3, kw: string = ""):Observable<PagedResponse<User>> {
    return this.http.get<PagedResponse<User>>(environment.backEndHost+"/users/"+page+"/"+size+"/"+kw).pipe(
      catchError(error => {
        return throwError(error);
      })
    );
  }*/
  listUsers(page: number = 0, size: number = 10, kw: string = ""): Observable<PagedResponse<User>> {
    // Construire les paramètres de requête HTTP
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('kw', kw);
    // Faire la requête HTTP en passant les paramètres
    return this.http.get<PagedResponse<User>>(environment.backEndHost+"/users", { params })
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }

  public updateUser(user: User, operateur: string): Observable<User> {
    return this.http.put<User>(environment.backEndHost+"/users/update/"+user.id+"/"+operateur, user);
  }

  public saveUser(user: User, operateur: string): Observable<any> {
    return this.http.post<any>(environment.backEndHost+"/users/save/"+operateur, user).pipe(
      catchError(error => {
        return throwError(error);
      })
    );
  }

  deleteUser(id: number, operateur: string):Observable<object> {
    return this.http.delete(environment.backEndHost+"/users/delete/"+id+"/"+operateur).pipe(
      catchError(error => {
        return throwError(error);
      })
    );
  }

  confirmPassword(id: number, password: string):Observable<boolean> {
    return this.http.get<boolean>(environment.backEndHost+"/confirm/"+id+"/"+password).pipe(
      catchError(error => {
        return throwError(error);
      })
    );
  }


}
