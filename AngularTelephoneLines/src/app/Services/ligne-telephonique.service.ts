import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {LoginService} from "./login.service";
import {LigneTelephonique} from "../Models/LigneTelephonique";
import {catchError, Observable, throwError} from "rxjs";
import {environment} from "../../environments/environment";
import {Rapprochement} from "../Models/Rapprochement";
import {PagedResponse} from "../Models/PagedResponse";

@Injectable({
  providedIn: 'root'
})
export class LigneTelephoniqueService {

  operateur!: string;
  constructor(private http: HttpClient,private userData: LoginService) {
    this.operateur = this.userData.getUserData().username;
  }

  //Attributs Services
  saveLigneTelephonique(ligneData: LigneTelephonique): Observable<any> {
    return this.http.post<string>(environment.backEndHost + "/telephonique/save/"+this.operateur, ligneData)
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }

  updateLigneTelephonique(ligneData: LigneTelephonique):Observable<any> {
    return this.http.put<string>(environment.backEndHost + "/telephonique/update/"+this.operateur, ligneData)
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }
  /*getAllLignes(): Observable<LigneTelephonique[]> {
    return this.http.get<LigneTelephonique[]>(environment.backEndHost + "/telephonique")
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }*/

  getAllLignes(page: number = 0, size: number = 10, kw: string = ""): Observable<PagedResponse<LigneTelephonique>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('kw', kw);
    return this.http.get<PagedResponse<LigneTelephonique>>(environment.backEndHost + "/telephonique", { params })
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }

  deleteTypeLigne(ligneId: number): Observable<any> {
    return this.http.delete<any>(environment.backEndHost + "/telephonique/delete/"+ligneId+"/"+this.operateur)
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }

  //Rapprochement
  getLignesRapprochement() {
    return this.http.get<Array<Rapprochement>>(environment.backEndHost+"/telephonique/rapprochement").pipe(
      catchError(error => {
        return throwError(error);
      })
    );
  }

  getAllLignesByType(idType: number | undefined): Observable<LigneTelephonique[]> {
    return this.http.get<LigneTelephonique[]>(environment.backEndHost + "/telephonique/type/"+idType)
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }

  getAllLignesByTypePage(page: number = 0, size: number = 10, kw: string = "", idType: number | undefined): Observable<PagedResponse<LigneTelephonique>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('kw', kw);
    return this.http.get<PagedResponse<LigneTelephonique>>(environment.backEndHost + "/telephonique/typeId/"+idType, { params })
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }


}
