import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, Observable, throwError} from 'rxjs';
import * as XLSX from 'xlsx';
import * as moment from 'moment';
import {VerificationResult} from "../Models/Rapprochement";
import {Attribut} from "../Models/Attribut";
import {EtatType, LigneTelephonique} from "../Models/LigneTelephonique";
import {TypeLigne} from "../Models/TypeLigne";
import {environment} from "../../environments/environment";
import {LoginService} from "./login.service";

@Injectable({
  providedIn: 'root'
})
export class ImportationService {
  operateur!: string;

  constructor(private http: HttpClient, private userData: LoginService) {
    this.operateur = this.userData.getUserData().username;
  }

  // Cette fonction prend une ligne du fichier Excel et les attributs du TypeLigne
  /*private createAttributs(row: any, typeLigneAttributs: Attribut[]): Attribut[] {
    const attributs: Attribut[] = [];
    for (const att of typeLigneAttributs) {
      if (row[att.nomAttribut.toLowerCase()]) {
        const attribut: Attribut = {
          ...att,
          valeurAttribut: row[att.nomAttribut.toLowerCase()]
        };
        attributs.push(attribut);
      }
    }
    return attributs;
  }*/
  private createAttributs(row: any, typeLigneAttributs: Attribut[]): Attribut[] {
    const attributs: Attribut[] = [];
    for (const att of typeLigneAttributs) {
      const valeur = row[att.nomAttribut.toLowerCase()] || null;
      const attribut: Attribut = {
        ...att,
        valeurAttribut: valeur
      };
      attributs.push(attribut);
    }
    return attributs;
  }

  dataFromExcelFile(file: File, typeLigne: TypeLigne): Observable<LigneTelephonique[]> {
    return new Observable<LigneTelephonique[]>((observer) => {
      const reader = new FileReader();

      reader.onload = (e) => {
        const data = new Uint8Array(reader.result as ArrayBuffer);
        const workbook = XLSX.read(data, { type: 'array' });
        const firstSheet = workbook.Sheets[workbook.SheetNames[0]];
        const jsonData = XLSX.utils.sheet_to_json(firstSheet);

        const lignes: LigneTelephonique[] = jsonData.map((row: any) => {
          let dateLivraison: Date | null = null;

          if (row['datelivraison']) {
            const parsedDate = moment(row['datelivraison'], ['YYYY-MM-DD', 'DD/MM/YYYY']);
            if (parsedDate.isValid()) {
              dateLivraison = parsedDate.toDate();
            }
          }

          const ligne: LigneTelephonique = {
            numeroLigne: row['numeroligne'] || null,
            affectation: row['affectation'] || null,
            poste: row['poste'] || null,
            etat: row['etat'] ? row['etat'] as EtatType : null,
            dateLivraison: dateLivraison,
            numeroSerie: row['numeroserie'] || null,
            montant: row['montant'] || null,
            createdDate: new Date(), // Date de création actuelle
            typeLigne: {
              ...typeLigne,
              attributs: this.createAttributs(row, typeLigne.attributs)
            }
          };
          return ligne;
        });

        observer.next(lignes);
        observer.complete();
      };

      reader.onerror = (error) => {
        observer.error(error);
      };

      reader.readAsArrayBuffer(file);
    });
  }

  /*dataFromExcelFile(file: File, typeLigne: TypeLigne): Observable<LigneTelephonique[]> {
    return new Observable<LigneTelephonique[]>((observer) => {
      const reader = new FileReader();

      reader.onload = (e) => {
        const data = new Uint8Array(reader.result as ArrayBuffer);
        const workbook = XLSX.read(data, { type: 'array' });
        const firstSheet = workbook.Sheets[workbook.SheetNames[0]];
        const jsonData = XLSX.utils.sheet_to_json(firstSheet);

        const lignes: LigneTelephonique[] = jsonData.map((row: any) => {
          const ligne: LigneTelephonique = {
            numeroLigne: row['numeroligne'],
            affectation: row['affectation'],
            poste: row['poste'],
            etat: row['etat'] as EtatType,
            dateLivraison: new Date(row['datelivraison']),
            numeroSerie: row['numeroserie'],
            montant: row['montant'],
            createdDate: new Date(), // Date de création actuelle
            typeLigne: {
              ...typeLigne,
              attributs: this.createAttributs(row, typeLigne.attributs)
            }
          };
          return ligne;
        });

        observer.next(lignes);
        observer.complete();
      };

      reader.onerror = (error) => {
        observer.error(error);
      };

      reader.readAsArrayBuffer(file);
    });
  }*/

  verifyExcelFileForImport(data: File, displayedColumns: string[]): Observable<VerificationResult> {
    return new Observable<VerificationResult>((observer) => {
      const fileReader = new FileReader();

      fileReader.onload = (e) => {
        const arrayBuffer = fileReader.result as ArrayBuffer;
        const data = new Uint8Array(arrayBuffer);
        const arr = [];

        for (let i = 0; i !== data.length; ++i) {
          arr[i] = String.fromCharCode(data[i]);
        }

        const bstr = arr.join('');
        const workbook = XLSX.read(bstr, { type: 'binary' });
        const first_sheet_name = workbook.SheetNames[0];
        const worksheet = workbook.Sheets[first_sheet_name];
        const jsonData = XLSX.utils.sheet_to_json(worksheet, { header: 1 }); // Convertir la feuille de calcul en JSON

        // Prendre les noms de colonnes à partir de la première ligne du fichier Excel
        const columnNamesFromFile = (jsonData[0] as string[]).map((col: string) => col.toLowerCase());

        // Convertir tous les noms de colonnes attendus en minuscules pour la comparaison
        const expectedColumns = displayedColumns.map((col: string) => col.toLowerCase());

        // Vérifier si toutes les colonnes attendues existent dans le fichier
        const missingColumns = expectedColumns.filter((col: string) => !columnNamesFromFile.includes(col));

        if (missingColumns.length > 0) {
          observer.next({ isValid: false, message: `Les colonnes suivantes sont manquantes : ${missingColumns.join(', ')}` });
        } else {
          observer.next({ isValid: true, message: 'Le fichier est valide.' });
        }

        observer.complete();
      };

      fileReader.readAsArrayBuffer(data);
    });
  }


  importDataToDB(importDataToDB: LigneTelephonique[]): Observable<any> {
    if (!this.operateur || !importDataToDB) {
      return throwError('Operateur ou données importées manquantes');
    }
    return this.http.post<any>(environment.backEndHost + "/telephonique/import/"+this.operateur, importDataToDB)
      .pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }




}