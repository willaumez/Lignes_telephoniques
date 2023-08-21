import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {User} from "../Models/User";
import {catchError, Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {}

  public listUsers():Observable<Array<User>> {
    return this.http.get<Array<User>>(environment.backEndHost+"/users");
  }

  public updateUser(user: User, operateur: string): Observable<User> {
    return this.http.put<User>(environment.backEndHost+"/users/update/"+user.id+"/"+operateur, user);
  }

  public saveUser(user: User, operateur: string): Observable<User> {
    return this.http.post<User>(environment.backEndHost+"/users/save/"+operateur, user);
  }

  public deleteUser(id: number, operateur: string):Observable<object> {
    return this.http.delete(environment.backEndHost+"/users/delete/"+id+"/"+operateur);
  }

  public confirmPassword(id: number, password: string):Observable<boolean> {
    return this.http.get<boolean>(environment.backEndHost+"/confirm/"+id+"/"+password);
  }


}
