import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {PagedResponse, RestoreResponse} from "../Models/PagedResponse";
import {environment} from "../../environments/environment";
import {Corbeille} from "../Models/Corbeille";
import {LoginService} from "./login.service";

@Injectable({
  providedIn: 'root'
})
export class CorbeilleService {
  operateur!: string;

  constructor(private http: HttpClient, private userData: LoginService) {
    this.operateur = this.userData.getUserData().username;
  }

  listCorbeille(page: number, size: number, kw: string):Observable<PagedResponse<Corbeille>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('kw', kw);
    return this.http.get<PagedResponse<Corbeille>>(environment.backEndHost+"/corbeille", { params }).pipe(
      catchError(error => {
        return throwError(error);
      })
    );
  }

  deleteCorbeille(id: number):Observable<object> {
    return this.http.delete(environment.backEndHost+"/corbeille/delete/"+id+"/"+this.operateur).pipe(
      catchError(error => {
        return throwError(error);
      })
    );
  }

  deleteAllCorbeille(): Observable<any> {
    return this.http.delete(environment.backEndHost+"/corbeille/delete/all/"+this.operateur).pipe(
      catchError(error => {
        return throwError(error);
      })
    );
  }

  restorCorbeille(idCorbeille: number):Observable<object> {
    return this.http.get(environment.backEndHost+"/corbeille/restaurer/"+idCorbeille+"/"+this.operateur).pipe(
      catchError(error => {
        return throwError(error);
      })
    );
  }

  restorCorbeilleAll():Observable<RestoreResponse> {
    return this.http.get<RestoreResponse>(environment.backEndHost+"/corbeille/restaurer/all/"+this.operateur).pipe(
      catchError(error => {
        return throwError(error);
      })
    );
  }


}
