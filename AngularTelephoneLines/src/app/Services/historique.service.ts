import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {Historique} from "../Models/Historique";

@Injectable({
  providedIn: 'root'
})
export class HistoriqueService {

  constructor(private http: HttpClient) { }

  listHistoriques():Observable<Array<Historique>> {
    return this.http.get<Array<Historique>>(environment.backEndHost+"/historiques");
  }

  deleteHistorique(id: number):Observable<object> {
    return this.http.delete(environment.backEndHost+"/historiques/delete/"+id);
  }

  deleteAllHistorique(operateur: string): Observable<any> {
    return this.http.delete(environment.backEndHost+"/historiques/delete/all/"+operateur);
  }

}
