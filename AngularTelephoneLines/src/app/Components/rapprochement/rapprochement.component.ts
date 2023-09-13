import {ChangeDetectorRef, Component, ElementRef, NgModule, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Rapprochement, RapprochementMontant, VerificationResult} from "../../Models/Rapprochement";
import {MatSort} from "@angular/material/sort";
import {LigneTelephoniqueService} from "../../Services/ligne-telephonique.service";
import {RapprochementService} from "../../Services/rapprochement.service";
import {MatTabGroup} from "@angular/material/tabs";
import * as XLSX from 'xlsx';


@Component({
  selector: 'app-rapprochement',
  templateUrl: './rapprochement.component.html',
  styleUrls: ['./rapprochement.component.scss']
})
export class RapprochementComponent implements OnInit {
  indexTab: number = 0;
  @ViewChild('tabGroup') tabGroup!: MatTabGroup;

  //Variable importation
  dataFromExcel: Rapprochement[] = [];
  dataFromDataBase: Rapprochement[] = [];

  //pointeurs
  isLoading: boolean = false;
  isExport: boolean = false;
  errorMessage!: string;
  verificationFile?: VerificationResult;
  isFileDragging: boolean = false;
  selectedFile!: File | null;
  traitement: boolean = false;
  traitementFinit: boolean = false;
  resultats: boolean = false;
  titreResultats!: string;
  dataMontant: boolean = false;

  //Données rapprochement
  dataCorresponding: Rapprochement[] = [];
  dataNotCorresponding: Rapprochement[] = [];
  dataMontantNoCor: RapprochementMontant[] = [];
  dataNoExistDB: Rapprochement[] = [];
  dataNoExistExcel: Rapprochement[] = [];

  //Table
  @ViewChild(MatSort) sort!: MatSort;
  displayedColumns: string[] = ['numero', 'montant'];
  displayedColumnsMontant: string[] = ['numero', 'montantDb', 'montantExcel'];
  dataSource = new MatTableDataSource<any>([]);

  @ViewChild('fileInput') fileInput!: ElementRef;

  ngOnInit(): void {
    this.indexTab = 0;
    this.getListLignesRapprochement();
  }

  constructor(private cdRef: ChangeDetectorRef, private rapService: RapprochementService, private ligneService: LigneTelephoniqueService) {

  }

  //DataBase
  getListLignesRapprochement() {
    this.ligneService.getLignesRapprochement().subscribe({
      next: (data) => {
        this.dataFromDataBase = data;
      },
      error: err => {
        this.errorMessage = "Erreur lors de la récupération des lignes téléphoniques" + err.error.message;
        console.log(err);
      }
    });
  }

  // Fonction pour passer à l'onglet suivant
  nextTab() {
    if (this.tabGroup && this.indexTab < this.tabGroup._tabs.length - 1) {
      this.indexTab++;  // Augmenter indexTab
      this.tabGroup.selectedIndex = this.indexTab;
    }
    this.cdRef.detectChanges();  // Forcer la détection des changements
  }

  previousTab() {
    this.resetImport();
    if (this.tabGroup && this.indexTab > 0) {
      this.indexTab--;  // Réduire indexTab
      this.tabGroup.selectedIndex = this.indexTab;
    }
    this.cdRef.detectChanges();  // Forcer la détection des changements
  }

  isTabDisabled(index: number): boolean {
    return index !== this.indexTab;
  }

  //Fonctions Importation
  getFile(event: any): void {
    if (event.target.files[0]) {
      this.isLoading = true;
      this.selectedFile = event.target.files[0];
      if (this.selectedFile) {

        this.rapService.verifyExcelFile(this.selectedFile).subscribe({
            next: (data: VerificationResult): void => {
              this.verificationFile = data;
              if (this.verificationFile.isValid) {
                this.extractDataFromExcel();
              }
              console.log("dataFromExcel :::   " + JSON.stringify(this.verificationFile));
            },
            error: (error) => {
              console.error('Une erreur est survenue lors de l\'importation des données depuis Excel', error);
            }
          }
        );
        this.isLoading = false;
      } else {
        this.errorMessage = "Aucun fichier détecté."
      }
      this.isLoading = false;
    }
  }

  preventDefault(event: Event): void {
    event.preventDefault();
  }

  onFileDragOver(event: DragEvent): void {
    this.isFileDragging = true;
    event.preventDefault();
  }

  onFileDragLeave(event: DragEvent): void {
    this.isFileDragging = false;
    event.preventDefault();
  }

  onFileDrop(event: DragEvent): void {
    this.isFileDragging = false;
    event.preventDefault();
    if (event.dataTransfer?.files) {
      this.isLoading = true;
      const files = event.dataTransfer?.files;
      if (files && files.length > 0) {
        this.selectedFile = files[0];
        this.isLoading = false;
        //this.dataFromExcel = this.rapService.importDataFromExcel(this.selectedFile);
      } else {
        this.errorMessage = "Erreur d'insertion du fichier s'il vous plaît réessayer!"
      }
      this.isLoading = false;
    }
  }

  resetImport() {
    this.dataFromExcel = [];
    this.isLoading = false;
    this.isFileDragging = false;
    this.selectedFile = null;
    this.traitement = false;
    this.traitementFinit = false;
    this.errorMessage = '';

    //data
    this.dataCorresponding = [];
    this.dataMontantNoCor = [];
    this.dataNoExistDB = [];
    this.dataNoExistExcel = [];

    // Réinitialiser le composant d'entrée de fichier
    this.fileInput.nativeElement.value = '';
  }

  extractDataFromExcel(): void {
    if (this.selectedFile) {
      this.rapService.importDataFromExcel(this.selectedFile).subscribe({
          next: (data: Rapprochement[]): void => {
            this.dataFromExcel = data;
            this.cdRef.detectChanges();
          },
          error: (error) => {
            console.error('Erreur lors de l\'importation du fichier :', error);
          }
        }
      );
    }
  }

  //Traitement
  validerTraitement(): void {
    this.traitement = true;
    // Comparaison des numeros
    for (const excelItem of this.dataFromExcel) {
      let numeroExisteDansDB = false;

      for (const dbItem of this.dataFromDataBase) {
        if (excelItem.numero === dbItem.numero) {
          this.dataCorresponding.push(dbItem);
          numeroExisteDansDB = true;
          break; // Passer au prochain élément de dataFromExcel dès qu'une correspondance est trouvée
        }
      }
      if (!numeroExisteDansDB) {
        this.dataNoExistDB.push(excelItem);
        this.dataNotCorresponding.push(excelItem)
      }
    }

    // Comparaison des numeros montant
    for (const correspondingItem of this.dataCorresponding) {
      const excelItem = this.dataFromExcel.find(excelItem => excelItem.numero === correspondingItem.numero);

      if (excelItem && excelItem.montant !== correspondingItem.montant) {
        const data: RapprochementMontant = {
          numero: correspondingItem.numero,
          montantDb: correspondingItem.montant,
          montantExcel: excelItem.montant
        };
        this.dataMontantNoCor.push(data);
      }
    }

    // Comparaison des données inexistantes dans l'autre sens
    for (const dbItem of this.dataFromDataBase) {
      let numeroExisteDansExcel = false;

      for (const excelItem of this.dataFromExcel) {
        if (dbItem.numero === excelItem.numero) {
          numeroExisteDansExcel = true;
          break;
        }
      }

      if (!numeroExisteDansExcel) {
        this.dataNoExistExcel.push(dbItem);
      }
    }
    //
    setTimeout(() => {
      this.traitement = false;
      this.traitementFinit = true;
    }, 1000);

  }


  //Resultats Traitement
  resultatsTraitement(): void {
    this.resultats = true;
  }


  //Charger les données
  chargerDataSource(data: any): void {
    this.isLoading = true;
    this.dataSource = new MatTableDataSource(data);
    if (this.sort) {
      this.dataSource.sort = this.sort;
    }
    setTimeout(() => {
      this.isLoading = false;
    }, 500);
  }

  dataOfBase(): void {
    this.dataMontant = false;
    this.titreResultats = "Lignes téléphoniques de la base de donnée";
    this.chargerDataSource(this.dataFromDataBase);
  }

  dataOfExcel(): void {
    this.dataMontant = false;
    this.titreResultats = "Lignes téléphoniques du fichier Excel";
    this.chargerDataSource(this.dataFromExcel);
  }

  dataCorresp(): void {
    this.dataMontant = false;
    this.titreResultats = "Lignes téléphoniques correspondantes";
    this.chargerDataSource(this.dataCorresponding);
  }

  dataNotCorresp(): void {
    this.dataMontant = false;
    this.titreResultats = "Lignes téléphoniques non correspondantes";
    this.chargerDataSource(this.dataNotCorresponding);
  }

  dataNotCorrespMontant(): void {
    this.titreResultats = "Numéros correspondants mais montants différents";
    this.dataMontant = true;
    this.chargerDataSource(this.dataMontantNoCor);
  }

  dataNotExistDataBase(): void {
    this.dataMontant = false;
    this.titreResultats = "Lignes téléphoniques dans le fichier Excel et non dans la base de donnée";
    this.chargerDataSource(this.dataNoExistDB);
  }

  dataNotExistExcel(): void {
    this.dataMontant = false;
    this.titreResultats = "Numéros de téléphone dans la base de donnée, mais pas dans le fichier Excel";
    this.chargerDataSource(this.dataNoExistExcel);
  }

  //Recherche table
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

//Exportation
  exportToExcel(titre: string) {
    this.isExport = true;
    const rawData = this.dataSource.data;
    const ws: XLSX.WorkSheet = {};
    ws['A1'] = {v: titre, t: 's'};
    ws['!merges'] = [{s: {r: 0, c: 0}, e: {r: 0, c: 1}}];

    if (titre == "Numéros correspondants mais montants différents") {
      ws['A2'] = {v: 'Numéro', t: 's'};
      ws['B2'] = {v: 'Montant Base de données', t: 's'};
      ws['C2'] = {v: 'Montant Fichier Excel', t: 's'};
      rawData.forEach((row, index) => {
        const rowIndex = index + 3;
        ws['A' + rowIndex] = {v: row.numero, t: 's'};
        ws['B' + rowIndex] = {v: row.montantDb, t: 'n'};
        ws['C' + rowIndex] = {v: row.montantExcel, t: 'n'};
      });
      const range = {s: {c: 0, r: 0}, e: {c: 2, r: rawData.length + 2}};
      ws['!ref'] = XLSX.utils.encode_range(range);
      ws['!cols'] = [
        {wch: 38},
        {wch: 38},
        {wch: 38}
      ];
    } else {
      ws['A2'] = {v: 'Numéro', t: 's'};
      ws['B2'] = {v: 'Montant', t: 's'};

      rawData.forEach((row, index) => {
        const rowIndex = index + 3;
        ws['A' + rowIndex] = {v: row.numero, t: 's'};
        ws['B' + rowIndex] = {v: row.montant, t: 'n'};
      });

      const range = {s: {c: 0, r: 0}, e: {c: 1, r: rawData.length + 2}};
      ws['!ref'] = XLSX.utils.encode_range(range);

      ws['!cols'] = [
        {wch: 38},
        {wch: 38}
      ];
    }

    // Appliquer le style de format "Text" à la première colonne
    for (let i = 1; i <= rawData.length + 2; i++) {
      const cell_address = {c: 0, r: i};
      const cell_ref = XLSX.utils.encode_cell(cell_address);
      if (!ws[cell_ref]) continue;
      if (!ws[cell_ref].z) {
        ws[cell_ref].z = '@';
      }
    }
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
    XLSX.writeFile(wb, 'rapprochement.xlsx');
    setTimeout(() => {
      this.isExport = false;
    }, 1000);

  }


}
