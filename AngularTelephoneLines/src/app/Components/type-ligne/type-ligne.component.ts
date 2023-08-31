import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatDialog} from "@angular/material/dialog";
import {CoreService} from "../../core/core.service";
import {LigneTelephoniqueService} from "../../Services/ligne-telephonique.service";
import {TypeAttributService} from "../../Services/type-attribut.service";
import {LigneTelephonique} from "../../Models/LigneTelephonique";
import {TypeLigne} from "../../Models/TypeLigne";
import {Attribut} from "../../Models/Attribut";
import {animate, style, transition, trigger} from "@angular/animations";
import {TypeLigneAddEditComponent} from "./type-ligne-add-edit/type-ligne-add-edit.component";

@Component({
  selector: 'app-type-ligne',
  templateUrl: './type-ligne.component.html',
  styleUrls: ['./type-ligne.component.scss'],
  animations: [
    trigger('slideInOut', [
      transition(':enter', [
        style({transform: 'translateX(100%)', transformOrigin: 'right center'}),
        animate('0.3s ease-out', style({transform: 'translateX(0%)'}))
      ]),
      transition(':leave', [
        animate('0.3s ease-in', style({transform: 'translateX(100%)'}))
      ])
    ]),
  ]
})

export class TypeLigneComponent implements OnInit{
  typeLignes!: TypeLigne[];
  selectedTypeLigne!: TypeLigne;
  typeLigneAttributs!: Attribut[]


  errorMessage!: string;
  columnsInit: string[] = ['idLigne', 'typeLigne.nomType', 'numeroLigne', 'affectation', 'poste', 'etat', 'dateLivraison', 'numeroSerie', 'montant', 'createdDate'];
  displayedColumns: string[] = [];
  attributNames: string[] = [];

  @Input()
  dataSource!: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private _dialog: MatDialog, private _coreService: CoreService, private ligneService: LigneTelephoniqueService,
              private attributService: TypeAttributService, private typeAttributService: TypeAttributService) {
  }

  ngOnInit(): void {
    this.getAllTypesLignes();
    //this.getLignesTelephonique();
  }

  //Forme Add Edit
  openAddEditLignForm() {
    const data: any = { add: true, typeLigne: this.selectedTypeLigne };
    //data.add(add);
    const dialogRef = this._dialog.open(TypeLigneAddEditComponent, {
      data
    });
    dialogRef.afterClosed().subscribe({
      next: (val) => {
        if (val) {
          this.selectedRowIndex = -1;
          this.ligneRow = {} as LigneTelephonique;
          this.getLignesTelephonique();
        }
      },
    });
    //this.getListLignes();
  }

  //ligneRow: {} = {};
  ligneRow: LigneTelephonique = {} as LigneTelephonique;

  selectedRowIndex: number = -1;
  buttonActionState = 'out';

  selectedRow(element: any, index: number) {
    if (this.selectedRowIndex === index) {
      // Annuler la sélection si l'utilisateur clique à nouveau sur la même ligne
      this.selectedRowIndex = -1;
      // Réinitialiser l'objet ligneRow
      this.ligneRow = {} as LigneTelephonique;
    } else {
      // Sinon, mettre à jour la ligne sélectionnée et l'objet ligneRow
      this.selectedRowIndex = index;
      this.ligneRow = element;
    }
    this.buttonActionState = 'in';
  }
  EditLigne() {
    if (this.ligneRow) {
      const data: any = { edit: true, ligne: this.ligneRow };
      const dialogRef = this._dialog.open(TypeLigneAddEditComponent, {
        data,
      });
      dialogRef.afterClosed().subscribe({
        next: (val) => {
          if (val) {
            //this.selectedRowIndex = -1;
            //this.ligneRow = {} as LigneTelephonique;
            this.getLignesTelephonique();
          }
        },
      });
    }
  }
  DeleteLigne(idLigne: number | undefined): void {
    let conf: boolean = confirm("Êtes-vous sure de supprimer cette Ligne Téléphonique ?")
    if (!conf) return;
    if (this.ligneRow && idLigne) {
      this.ligneService.deleteTypeLigne(idLigne).subscribe({
          next: (data: any): void => {
            this.getLignesTelephonique();
            this._coreService.openSnackBar("Ligne Téléphonique  supprimé avec succès !");
          },
          error: (error) => {
            this._coreService.openSnackBar(error.error.message);
          }
        }
      );
    }
  }


  //Pour le zoom
  isObjectEmpty(obj: any): boolean {
    return Object.keys(obj).length === 0;
  }
  //zoom table
  zoomLevel: number = 1;
  zoomIn(): void {
    this.zoomLevel += 0.1;  // Augmente de 10%
  }
  zoomInit(): void {
    this.zoomLevel = 1;  // Augmente de 10%
  }
  zoomOut(): void {
    if (this.zoomLevel > 0.1) {  // On ne peut pas avoir un zoomLevel inférieur à 0.1 (10%)
      this.zoomLevel -= 0.1;  // Diminue de 10%
    }
  }


  //fonctions
  getAllTypesLignes(): void {
    this.typeAttributService.getAllTypesLigne().subscribe({
        next: (data: any[]): void => {
          this.typeLignes = data;
          if (this.typeLignes && this.typeLignes.length > 0) {
            this.selectedTypeLigne = this.typeLignes[0];
            this.getLignesTelephonique();
          }else {
            this.errorMessage = "Aucun type de ligne téléphonique";
          }
          //console.log("getAllTypesLignes--  "+JSON.stringify(this.typeLignes))
        },
        error: (error) => {
          this.errorMessage = ('Erreur lors de la récupération des types de ligne: ' + error.error.message)
          this._coreService.openSnackBar('Erreur lors de la récupération des types de ligne:' + error.error.message);
        }
      }
    );
  }
  selectTypeLigne(typeLigne: TypeLigne): void {
    this.selectedTypeLigne = typeLigne;
    this.displayedColumns = [];
    this.currentIndex = this.typeLignes.indexOf(typeLigne);
    this.elementSelected();
    console.log("selectTypeLigne-- "+JSON.stringify(this.selectedTypeLigne));
    this.getLignesTelephonique();
  }
  getLignesTelephonique(): void {
    this.selectedRowIndex = -1;
    this.ligneRow = {} as LigneTelephonique;
    this.getAttributNames();
    this.ligneService.getAllLignesByType(this.selectedTypeLigne.idType).subscribe({
        next: (data: any[]): void => {
          this.dataSource = new MatTableDataSource(data);

          // Initialiser le paginator et le sort ici
          this.dataSource.sort = this.sort;
          this.dataSource.paginator = this.paginator;
        },
        error: (error) => {
          this.errorMessage = "Erreur lors de la récupération des types de ligne";
          this._coreService.openSnackBar('Erreur lors de la récupération des Noms de types de ligne:' + error.error.message);
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
  findAttributValue(ligneAttributs: any[], attributName: string): string {
    const attributObj = ligneAttributs.find(attr => attr.attribut.nomAttribut === attributName);
    return attributObj ? attributObj.valeurAttribut : '   ';
  }

  // Pour le filtre
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }


  // Variable pour suivre l'index de l'élément sélectionné
  currentIndex: number = 0;
  firstElement() {
    this.currentIndex = 0;
    this.selectedTypeLigne = this.typeLignes[this.currentIndex];
    this.elementSelected();
    this.getLignesTelephonique();
  }
  previousElement() {
    if (this.currentIndex > 0) {
      this.currentIndex--;
      this.selectedTypeLigne = this.typeLignes[this.currentIndex];
      this.elementSelected();
      this.getLignesTelephonique();
    }
  }
  nextElement() {
    if (this.currentIndex < this.typeLignes.length - 1) {
      this.currentIndex++;
      this.selectedTypeLigne = this.typeLignes[this.currentIndex];
      this.elementSelected();
      this.getLignesTelephonique();
    }
  }
  lastElement() {
    this.currentIndex = this.typeLignes.length - 1;
    this.selectedTypeLigne = this.typeLignes[this.currentIndex];
    this.elementSelected();
    this.getLignesTelephonique();
  }
  elementSelected(){
    this.ligneRow = {} as LigneTelephonique;
    this.selectedRowIndex = -1;
    if (this.selectedTypeLigne.idType !== undefined) {
      const element = document.getElementById(this.selectedTypeLigne.idType.toString());
      element?.scrollIntoView({ behavior: "smooth", block: "nearest", inline: "center" });
    }
  }






}
