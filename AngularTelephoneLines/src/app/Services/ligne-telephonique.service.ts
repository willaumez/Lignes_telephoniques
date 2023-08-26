import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LoginService} from "./login.service";
import {LigneTelephonique} from "../Models/LigneTelephonique";
import {catchError, Observable, throwError} from "rxjs";
import {environment} from "../../environments/environment";
import {Attribut} from "../Models/Attribut";

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
    console.log(ligneData)
    return this.http.post<string>(environment.backEndHost + "/telephonique/save/"+this.operateur, ligneData)
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }
  getAllLignes(): Observable<LigneTelephonique[]> {
    return this.http.get<LigneTelephonique[]>(environment.backEndHost + "/telephonique")
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }

}
