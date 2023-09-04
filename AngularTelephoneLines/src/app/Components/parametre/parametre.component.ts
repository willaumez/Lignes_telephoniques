import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {TypeLigne} from "../../Models/TypeLigne";
import {TypeAttributService} from "../../Services/type-attribut.service";
import {CoreService} from "../../core/core.service";
import {Attribut} from "../../Models/Attribut";
import {Rapprochement, VerificationResult} from "../../Models/Rapprochement";
import {ImportationService} from "../../Services/importation.service";
import {LigneTelephonique} from "../../Models/LigneTelephonique";

@Component({
  selector: 'app-parametre',
  templateUrl: './parametre.component.html',
  styleUrls: ['./parametre.component.scss']
})
export class ParametreComponent implements OnInit{
  typeLignes!: TypeLigne[];
  selectedTypeLigne!: TypeLigne;
  errorMessage!: string;

  attributNames: string[] = [];
  typeLigneAttributs!: Attribut[];
  columnsInit: string[] = ['numeroLigne', 'affectation', 'poste', 'etat', 'dateLivraison', 'numeroSerie', 'montant'];
  displayedColumns: string[] = [];

  //Fichier
  selectedFile!: File;
  isLoading: boolean = false;
  isFileDragging: boolean = false;
  verificationFile?: VerificationResult;
  importation: boolean = false;

  //import
  importDataToDB: LigneTelephonique[] = [];

  @ViewChild('fileInput') fileInput!: ElementRef;

  constructor(private typeAttributService: TypeAttributService, private _coreService: CoreService, private importationService: ImportationService) {
  }

  ngOnInit(): void {
    this.getAllTypesLignes();
  }

  //fonctions
  getAllTypesLignes(): void {
    this.typeAttributService.getAllTypesLigne().subscribe({
        next: (data: any[]): void => {
          this.typeLignes = data;
          if (this.typeLignes && this.typeLignes.length > 0) {
            this.selectedTypeLigne = this.typeLignes[0];
            this.getAttributNames();
          }else {
            this.errorMessage = "Aucun type de ligne téléphonique";
          }
        },
        error: (error) => {
          this.errorMessage = ('Erreur lors de la récupération des types de ligne: ' + error.error.message)
          this._coreService.openSnackBar('Erreur lors de la récupération des types de ligne:' + error.error.message);
        }
      }
    );
  }
  getAttributNames(): void {
    if (this.typeLignes){
      this.attributNames = [];
      this.typeLigneAttributs = this.selectedTypeLigne.attributs;
      if (this.typeLigneAttributs) {
        this.typeLigneAttributs.forEach((attribut:Attribut) => {
          this.attributNames.push(attribut.nomAttribut);
        });
        this.displayedColumns = [...this.columnsInit, ...this.attributNames];
      }
    }

  }
  selectTypeLigne(typeLigne: TypeLigne): void {
    this.selectedTypeLigne = typeLigne;
    this.getAttributNames();
    this.currentIndex = this.typeLignes.indexOf(typeLigne);
    this.elementSelected();
  }

  // Variable pour suivre l'index de l'élément sélectionné
  currentIndex: number = 0;
  firstElement() {
    this.currentIndex = 0;
    this.selectedTypeLigne = this.typeLignes[this.currentIndex];
    this.elementSelected();
  }
  previousElement() {
    if (this.currentIndex > 0) {
      this.currentIndex--;
      this.selectedTypeLigne = this.typeLignes[this.currentIndex];
      this.elementSelected();
    }
  }
  nextElement() {
    if (this.currentIndex < this.typeLignes.length - 1) {
      this.currentIndex++;
      this.selectedTypeLigne = this.typeLignes[this.currentIndex];
      this.elementSelected();
    }
  }
  lastElement() {
    this.currentIndex = this.typeLignes.length - 1;
    this.selectedTypeLigne = this.typeLignes[this.currentIndex];
    this.elementSelected();
  }
  elementSelected() {
    this.resetImport();
    if (this.selectedTypeLigne && this.selectedTypeLigne.idType !== undefined) {
      const element = document.getElementById(this.selectedTypeLigne.idType.toString());
      if (element) {
        element.scrollIntoView({ behavior: "smooth", block: "nearest", inline: "center" });
      }
    }
  }

  resetImport():void {
    this.isLoading = false;
    this.isFileDragging = false;
    this.selectedFile = null as any;
    this.importation = false;
    this.errorMessage = '';
    this.verificationFile = undefined;

    // Réinitialiser le composant d'entrée de fichier
    this.fileInput.nativeElement.value = '';
  }




  //Fichier Excel
  getFile(event: any): void {
    if (event.target.files[0]) {
      this.isLoading = true;
      this.selectedFile = event.target.files[0];
      if (this.selectedFile) {
        this.importationService.verifyExcelFileForImport(this.selectedFile, this.displayedColumns).subscribe({
            next: (data: VerificationResult): void => {
              this.verificationFile = data;
              if (this.verificationFile.isValid) {
                this.extractDataFromExcel();
              }
              console.log("dataFromExcel :::   " + JSON.stringify(this.verificationFile, null, 2));
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
  extractDataFromExcel(): void {
    if (this.selectedFile) {
      this.importationService.dataFromExcelFile(this.selectedFile, this.selectedTypeLigne).subscribe({
          next: (data: LigneTelephonique[]): void => {
            this.importDataToDB = data;
            //console.log("importationService.dataFromExcelFile :::   " + JSON.stringify(data, null, 2));
            //this.cdRef.detectChanges();
          },
          error: (error) => {
            console.error('Erreur lors de l\'importation du fichier :', error);
          }
        }
      );
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


  //Importation
  validerImportation():void{
    this.importation = true;

    if (this.importDataToDB.length>0) {
      this.importationService.importDataToDB(this.importDataToDB).subscribe({
          next: (data: any): void => {
            console.log("savedCount :::   " + JSON.stringify(data?.savedCount, null, 2));
            console.log("notSavedCount :::   " + JSON.stringify(data?.notSavedCount, null, 2));
            console.log("failedNumbers :::   " + JSON.stringify(data?.failedNumbers, null, 2));
            //this.cdRef.detectChanges();

            this.importation = false;
          },
          error: (error) => {
            console.error('Erreur lors de l\'importation du fichier :', error);
            this.errorMessage = "Erreur lors de l\'importation du fichier :"+error.error.message;
          }
        }
      );
      //this.errorMessage = "Erreur lors de l\'importation du fichier :";
    }

/*
    setTimeout(() => {
      this.importation = false;
    }, 1000);*/

  }



}
