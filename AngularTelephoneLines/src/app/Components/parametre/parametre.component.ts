import {Component, OnInit} from '@angular/core';
import {TypeLigne} from "../../Models/TypeLigne";
import {TypeAttributService} from "../../Services/type-attribut.service";
import {CoreService} from "../../core/core.service";
import {Attribut} from "../../Models/Attribut";
import {VerificationResult} from "../../Models/Rapprochement";

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
  columnsInit: string[] = ['numeroLigne', 'affectation', 'poste', 'etat', 'dateLivraison', 'numeroSerie', 'montant', 'createdDate'];
  displayedColumns: string[] = [];

  //Fichier
  selectedFile: any;
  isLoading: boolean = false;
  isFileDragging: boolean = false;

  constructor(private typeAttributService: TypeAttributService, private _coreService: CoreService) {
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
  elementSelected(){
    if (this.selectedTypeLigne.idType !== undefined) {
      const element = document.getElementById(this.selectedTypeLigne.idType.toString());
      element?.scrollIntoView({ behavior: "smooth", block: "nearest", inline: "center" });
    }
  }



  //Fichier Excel
  getFile(event: any): void {
    if (event.target.files[0]) {
      this.isLoading = true;
      this.selectedFile = event.target.files[0];
      if (this.selectedFile) {
        /*this.rapService.verifyExcelFile(this.selectedFile).subscribe({
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

        );*/
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



}
