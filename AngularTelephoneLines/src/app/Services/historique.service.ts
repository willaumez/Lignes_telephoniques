import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {environment} from "../../environments/environment";
import {Historique} from "../Models/Historique";
import {PagedResponse} from "../Models/PagedResponse";

@Injectable({
  providedIn: 'root'
})
export class HistoriqueService {

  constructor(private http: HttpClient) { }

  listHistoriques(page: number, size: number, kw: string):Observable<PagedResponse<Historique>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('kw', kw);
    return this.http.get<PagedResponse<Historique>>(environment.backEndHost+"/historiques", { params }).pipe(
      catchError(error => {
        return throwError(error);
      })
    );
  }

  deleteHistorique(id: number):Observable<object> {
    return this.http.delete(environment.backEndHost+"/historiques/delete/"+id).pipe(
      catchError(error => {
        return throwError(error);
      })
    );
  }

  deleteAllHistorique(operateur: string): Observable<any> {
    return this.http.delete(environment.backEndHost+"/historiques/delete/all/"+operateur).pipe(
      catchError(error => {
        return throwError(error);
      })
    );
  }



}
