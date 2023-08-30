import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import * as XLSX from 'xlsx';
import {Observable} from 'rxjs';
import {Rapprochement, VerificationResult} from '../Models/Rapprochement';

@Injectable({
  providedIn: 'root'
})
export class RapprochementService {
  constructor(private http: HttpClient) {
  }

  // Méthode pour importer les données du fichier Excel
  importDataFromExcel(file: File): Observable<Rapprochement[]> {
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
        const workbook = XLSX.read(bstr, {type: 'binary', cellDates: true});
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

  /*private extractLinesFromWorksheet(worksheet: any): Rapprochement[] {
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
  }*/

  private extractLinesFromWorksheet(worksheet: any): Rapprochement[] {
    const lines: Rapprochement[] = [];

    // Ajouter une variable pour suivre l'index de la ligne
    let rowIndex = 1;

    for (const cell in worksheet) {
      if (cell.includes('A')) {
        // Sauter la première ligne en vérifiant l'index de la ligne
        if (rowIndex > 1) {
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
        // Incrémenter l'index de la ligne
        rowIndex++;
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


// ...
/*
  isExcelFileValid(data: any): boolean {
    const workbook = XLSX.read(data, {
      type: 'array'
    });

    const firstSheetName = workbook.SheetNames[0];
    const worksheet = workbook.Sheets[firstSheetName];

    const rows = XLSX.utils.sheet_to_json(worksheet, {range: 1});  // Ignorer la première ligne

    if (rows.length === 0) {
      console.log('Le fichier est vide.');
      return false;
    }

    // Prendre les 5 premières lignes pour la vérification
    const sampleRows = rows.slice(0, 5) as Record<string, any>[];

    let lineNumberColumn = '';
    let lineAmountColumn = '';

    if (sampleRows.length > 0) {
      const columnNames = Object.keys(sampleRows[0]);

      for (const columnName of columnNames) {
        const isLineNumber = sampleRows.every(row =>
          (typeof row[columnName] === 'string' || typeof row[columnName] === 'number')
          && row[columnName].toString().startsWith('212')
        );

        const isLineAmount = sampleRows.every(row =>
          typeof row[columnName] === 'string' || typeof row[columnName] === 'number'
        );

        if (isLineNumber) {
          lineNumberColumn = columnName;
        }

        if (isLineAmount) {
          lineAmountColumn = columnName;
        }
      }
    }

    if (lineNumberColumn && lineAmountColumn) {
      console.log(`Le fichier est valide. Numéro de Ligne: ${lineNumberColumn}, Montant de la Ligne: ${lineAmountColumn}`);
      return true;
    } else {
      console.log('Le fichier n\'est pas valide.');
      return false;
    }
  }
*/

  // Fonction pour vérifier si le fichier est un fichier Excel valide
  verifyExcelFile(data: File): Observable<VerificationResult> {
    return new Observable<VerificationResult>((observer) => {
      const fileReader = new FileReader();

      fileReader.onload = (e) => {
        const arrayBuffer = fileReader.result as ArrayBuffer;
        const data = new Uint8Array(arrayBuffer);
        const arr = [];

        for (let i = 0; i !== data.length; i++) {
          arr[i] = String.fromCharCode(data[i]);
        }

        const bstr = arr.join('');
        const workbook = XLSX.read(bstr, { type: 'binary' });

        // Assumer que le fichier n'a qu'une seule feuille
        const first_sheet_name = workbook.SheetNames[0];
        const worksheet = workbook.Sheets[first_sheet_name];

        // Vérifier si les colonnes "A" et "B" existent
        if (!worksheet['A1'] || !worksheet['B1']) {
          observer.next({ isValid: false, message: 'Les colonnes "numero" et "montant" sont requises.' });
          observer.complete();
          return;
        }

        // Vérifier les données
        for (const cell in worksheet) {
          if (cell.startsWith('A') || cell.startsWith('B')) {
            const value = worksheet[cell]?.v;
            if (value === undefined || (typeof value !== 'string' && typeof value !== 'number')) {
              observer.next({ isValid: false, message: `Valeur invalide à la cellule ${cell}.` });
              observer.complete();
              return;
            }
          }
        }

        observer.next({ isValid: true, message: 'Le fichier est valide.' });
        observer.complete();
      };

      fileReader.onerror = (e) => {
        observer.next({ isValid: false, message: `Erreur de lecture du fichier : ${e}` });
        observer.complete();
      };

      fileReader.readAsArrayBuffer(data);
    });
  }


}
