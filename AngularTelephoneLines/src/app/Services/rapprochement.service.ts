import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as XLSX from 'xlsx';
import { Observable } from 'rxjs';
import { Rapprochement } from '../Models/Rapprochement';

@Injectable({
  providedIn: 'root'
})
export class RapprochementService {
  constructor(private http: HttpClient) {
  }

  // Méthode pour importer les données du fichier Excel
  public importDataFromExcel(file: File): Observable<Rapprochement[]> {
    return new Observable<Rapprochement[]>((observer) => {
      const fileReader = new FileReader();

      fileReader.onload = (e) => {
        const arrayBuffer = fileReader.result as ArrayBuffer;
        const data = new Uint8Array(arrayBuffer);
        const arr = [];

        for (let i = 0; i !== data.length; i++) {
          arr[i] = String.fromCharCode(data[i]);
        }

        const bstr = arr.join('');
        const workbook = XLSX.read(bstr, { type: 'binary', cellDates: true });
        const first_sheet_name = workbook.SheetNames[0];
        const worksheet = workbook.Sheets[first_sheet_name];

        const lines = this.extractLinesFromWorksheet(worksheet);
        observer.next(lines);
        observer.complete();
      };

      fileReader.onerror = (e) => {
        observer.error(e);
      };

      fileReader.readAsArrayBuffer(file);
    });
  }

  private extractLinesFromWorksheet(worksheet: any): Rapprochement[] {
    const lines: Rapprochement[] = [];

    for (const cell in worksheet) {
      if (cell.includes('A')) {
        const numero = worksheet[cell]?.v?.toString() ?? ''; // Vérifier et convertir en chaîne
        const montant = parseFloat(worksheet['B' + cell.substring(1)]?.v); // Utiliser parseFloat sans conversion en chaîne

        if (numero) {
          const line: Rapprochement = {
            numero: this.normalizeNumero(numero),
            montant: isNaN(montant) ? 0 : montant, // Assurer que le montant est un nombre valide
          };
          lines.push(line);
        }
      }
    }
    return lines;
  }

  private normalizeNumero(numero: string): string {
    // Mettez ici la logique pour normaliser les numéros selon vos besoins
    // (similaire à celle dans la fonction readNumerosEntrepriseFromExcel)
    // Exemple :
    if (numero.startsWith("0")) {
      return "212" + numero.substring(1);
    } else if (numero.startsWith("+")) {
      return numero.substring(1);
    } else if (numero.startsWith("7") || numero.startsWith("6")) {
      return "212" + numero;
    } else {
      return numero;
    }
  }


}
