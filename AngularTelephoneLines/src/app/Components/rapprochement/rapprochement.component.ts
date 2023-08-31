import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Rapprochement, VerificationResult} from "../../Models/Rapprochement";
import {MatSort} from "@angular/material/sort";
import {LigneTelephoniqueService} from "../../Services/ligne-telephonique.service";
import {RapprochementService} from "../../Services/rapprochement.service";
import {MatTabGroup} from "@angular/material/tabs";
import * as XLSX from 'xlsx';
import {Observable} from "rxjs";

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
  errorMessage!: string;
  verificationFile?: VerificationResult;
  isFileDragging: boolean = false;
  selectedFile!: File | null;
  traitement: boolean = false;
  traitementFinit: boolean = false;
  resultats: boolean = false;
  titreResultats!: string;

  //Données rapprochement
  dataCorresponding: Rapprochement[] = [];
  dataNotCorresponding: Rapprochement[] = [];
  dataMontantNoCor: Rapprochement[] = [];
  dataNoExistDB: Rapprochement[] = [];
  dataNoExistExcel: Rapprochement[] = [];

  //Table
  @ViewChild(MatSort) sort!: MatSort;
  displayedColumns: string[] = ['numero', 'montant'];
  dataSource!: MatTableDataSource<any>;
  /*dataBase!: MatTableDataSource<any>;
  tableCorresponding!: MatTableDataSource<any>;
  tableMontantNoCor!: MatTableDataSource<any>;
  tableNoExistDB!: MatTableDataSource<any>;
  tableNoExistExcel!: MatTableDataSource<any>;*/

  ngOnInit(): void {
    this.indexTab = 0;
    this.getListLignesRapprochement();
  }

  constructor(private cdRef: ChangeDetectorRef, private rapService: RapprochementService, private ligneService: LigneTelephoniqueService ) {

  }

  //DataBase
  getListLignesRapprochement() {
    this.ligneService.getLignesRapprochement().subscribe({
      next: (data) => {
        this.dataFromDataBase = data;
        //this.dataBase = new MatTableDataSource(data);
        //this.dataSource.sort = this.sort;
        //this.dataSource.paginator = this.paginator;
      },
      error: err => {
        this.errorMessage = "Erreur lors de la récupération des lignes téléphoniques"+err.error.message;
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

     /*   this.rapService.importDataFromExcel(this.selectedFile).subscribe({
            next: (data: Rapprochement[]): void => {
              this.dataFromExcel = data;
              console.log("dataFromExcel :::   " + JSON.stringify(this.dataFromExcel));
            },
            error: (error) => {
              console.error('Une erreur est survenue lors de l\'importation des données depuis Excel', error);
            }
          }
        );*/

        this.rapService.verifyExcelFile(this.selectedFile).subscribe({
            next: (data: VerificationResult): void => {
              this.verificationFile = data;
              if (this.verificationFile.isValid){
                this.extractDataFromExcel();
              }
              console.log("dataFromExcel :::   " + JSON.stringify(this.verificationFile));
            },
            error: (error) => {
              console.error('Une erreur est survenue lors de l\'importation des données depuis Excel', error);
            }
          }
        );

        //console.log("dataFromExcel :::   " + this.dataFromExcel);
        //console.log("valide--   --     "+this.rapService.isExcelFileValid(this.selectedFile))
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
  }

  extractDataFromExcel(): void {
    if (this.selectedFile) {
      this.rapService.importDataFromExcel(this.selectedFile).subscribe({
          next: (data: Rapprochement[]): void => {
            this.dataFromExcel = data;
            console.log("dataFromExcel        " + this.dataFromExcel);
            //this.dataSource.sort = this.sort;
            //this.dataSource.paginator = this.paginator;
            // Trigger manual change detection
            this.cdRef.detectChanges();
          },
          error: (error) => {
            console.error('Erreur lors de l\'importation du fichier :', error);
          }
        }
      );
    }
  }

  validerTraitement2(): void {
    if (this.selectedFile) {
      this.rapService.importDataFromExcel(this.selectedFile).subscribe({
          next: (data: Rapprochement[]): void => {
            this.dataFromExcel = data;
            console.log("dataFromExcel        " + this.dataFromExcel);
            //this.dataSource.sort = this.sort;
            //this.dataSource.paginator = this.paginator;
            // Trigger manual change detection
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
  validerTraitement():void {
    this.traitement = true;
    // Comparaison des numeros
    for (const excelItem of this.dataFromExcel) {
      let numeroExisteDansDB = false;

      for (const dbItem of this.dataFromDataBase) {
        if (excelItem.numero === dbItem.numero) {
          this.dataCorresponding.push(excelItem);
          numeroExisteDansDB = true;
          break; // Passer au prochain élément de dataFromExcel dès qu'une correspondance est trouvée
        }
      }

      if (!numeroExisteDansDB) {
        this.dataNoExistDB.push(excelItem);
      }
    }

    // Comparaison des numeros montant
    for (const correspondingItem of this.dataCorresponding) {
      const excelItem = this.dataFromExcel.find(excelItem => excelItem.numero === correspondingItem.numero);

      if (excelItem && excelItem.montant !== correspondingItem.montant) {
        this.dataMontantNoCor.push(correspondingItem);
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

    // Mettre à jour les sources de données pour les tables
    //this.tableCorresponding = new MatTableDataSource(this.dataCorresponding);
    //this.tableMontantNoCor = new MatTableDataSource(this.dataMontantNoCor);
    //this.tableNoExistDB = new MatTableDataSource(this.dataNoExistDB);
    //this.tableNoExistExcel = new MatTableDataSource(this.dataNoExistExcel);

    //
    setTimeout(() => {
      this.traitement = false;
      this.traitementFinit = true;
    }, 5000);


  }

  //Resultats Traitement
  resultatsTraitement():void{
    this.resultats = true;
  }
  //Charger les données
  dataOfBase():void{
    this.titreResultats = "Lignes téléphoniques de la base de donnée";
    this.dataSource = new MatTableDataSource(this.dataFromDataBase);
    this.dataSource.sort = this.sort;
  }
  dataOfExcel():void{
    this.titreResultats = "Lignes téléphoniques du fichier Excel";
    this.dataSource = new MatTableDataSource(this.dataFromExcel);
    this.dataSource.sort = this.sort;
  }
  dataCorresp():void{
    this.titreResultats = "Lignes téléphoniques correspondantes";
    this.dataSource = new MatTableDataSource(this.dataCorresponding);
    this.dataSource.sort = this.sort;
  }
  dataNotCorresp():void{
    this.titreResultats = "Lignes téléphoniques non correspondantes";
    this.dataSource = new MatTableDataSource(this.dataNotCorresponding);
    this.dataSource.sort = this.sort;
  }
  dataNotCorrespMontant():void{
    this.titreResultats = "Numéros correspondants mais montants différents";
    this.dataSource = new MatTableDataSource(this.dataMontantNoCor);
    this.dataSource.sort = this.sort;
  }
  dataNotExistDataBase():void{
    this.titreResultats = "Lignes téléphoniques dans le fichier Excel et non dans la base de donnée";
    this.dataSource = new MatTableDataSource(this.dataNoExistDB);
    this.dataSource.sort = this.sort;
  }
  dataNotExistExcel():void{
    this.titreResultats = "Numéros de téléphone dans la base de donnée, mais pas dans le fichier Excel";
    this.dataSource = new MatTableDataSource(this.dataNoExistExcel);
    this.dataSource.sort = this.sort;
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
  /*exportToExcel() {
    // Accéder aux données réelles depuis MatTableDataSource
    const rawData:Rapprochement[] = this.dataSource.data;

    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(rawData);
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
    XLSX.writeFile(wb, 'rapprochement.xlsx');
  }*/
  exportToExcel() {
    // Accéder aux données réelles depuis MatTableDataSource
    const rawData = this.dataSource.data;

    // Créer un WorkSheet
    const ws: XLSX.WorkSheet = XLSX.utils.aoa_to_sheet([
      ["Numéro (String)", "Montant (Double)"], // Les en-têtes des colonnes
      ...rawData.map(x => [String(x.numero), Number(x.montant)]) // Les données
    ]);

    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
    XLSX.writeFile(wb, 'rapprochement.xlsx');
  }





}
