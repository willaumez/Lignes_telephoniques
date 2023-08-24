import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {LoginService} from "./login.service";
import {environment} from "../../environments/environment";
import {Attribut} from "../Models/Attribut";
import {TypeLigne} from "../Models/TypeLigne";

@Injectable({
  providedIn: 'root'
})
export class TypeAttributService {

  operateur!: string;
  constructor(private http: HttpClient,private userData: LoginService) {
    this.operateur = this.userData.getUserData().username;
  }

  //Attributs Services
  saveAttribut(attributData: any): Observable<any> {
    return this.http.post<string>(environment.backEndHost + "/attributs/save/"+this.operateur, attributData)
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }

  getAttribut(attributId: number): Observable<any> {
    return this.http.get(environment.backEndHost + "/attributs/"+attributId)
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }

  getAllAttributs(): Observable<Attribut[]> {
    return this.http.get<Attribut[]>(environment.backEndHost + "/attributs")
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }

  updateAttribut(attributData: Attribut): Observable<string> {
    return this.http.put<any>(environment.backEndHost + "/attributs/update/"+this.operateur, attributData)
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }

  deleteAttribut(attributId: number): Observable<any> {
    return this.http.delete<any>(environment.backEndHost + "/attributs/delete/"+attributId+"/"+this.operateur)
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }


  //TypeLigne Services

  getAllTypesLigne(): Observable<TypeLigne[]> {
    return this.http.get<TypeLigne[]>(environment.backEndHost + "/typeLigne")
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }

  saveTypeLigne(typeData: TypeLigne): Observable<any> {
    return this.http.post<string>(environment.backEndHost + "/typeLigne/save/"+this.operateur, typeData)
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }

  deleteTypeLigne(typeId: number): Observable<any> {
    return this.http.delete<any>(environment.backEndHost + "/typeLigne/delete/"+typeId+"/"+this.operateur)
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }

  updateTypeLigne(typeData: TypeLigne): Observable<string> {
    return this.http.put<any>(environment.backEndHost + "/typeLigne/update/"+this.operateur, typeData)
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }



}
