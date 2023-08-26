import {Component, OnInit} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {UserAddEditComponent} from "../utilisateurs/user-add-edit/user-add-edit.component";
import {MatDialog} from "@angular/material/dialog";
import {LigneAddEditComponent} from "./ligne-add-edit/ligne-add-edit.component";
import {CoreService} from "../../core/core.service";
import {LigneTelephoniqueService} from "../../Services/ligne-telephonique.service";
import {TypeAttributService} from "../../Services/type-attribut.service";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-lignes-telephonique',
  templateUrl: './lignes-telephonique.component.html',
  styleUrls: ['./lignes-telephonique.component.scss'],
  animations: [
    trigger('slideInOut', [
      transition(':enter', [
        style({ transform: 'translateX(100%)', transformOrigin: 'right center' }),
        animate('0.3s ease-out', style({ transform: 'translateX(0%)' }))
      ]),
      transition(':leave', [
        animate('0.3s ease-in', style({ transform: 'translateX(100%)' }))
      ])
    ]),
  ]
})
export class LignesTelephoniqueComponent implements OnInit{
  displayedColumns: string[] = ['idLigne', 'numeroLigne', 'affectation', 'poste', 'etat', 'dateLivraison', 'numeroSerie', 'montant', 'createdDate', 'typeLigne.nomType'];
  attributNames: string[] = [];
  dataSource!: MatTableDataSource<any>;

  constructor( private _dialog: MatDialog, private _coreService: CoreService, private ligneService: LigneTelephoniqueService,
               private attributService: TypeAttributService) {
  }

  ngOnInit(): void {
        this.getLignesTelephonique();
        this.getAttributNames();
    }

  ligneRow: {} = {};
  selectedRowIndex: number = -1;
  buttonActionState = 'out';
  selectedRow(element: any, index: number) {
    if (this.selectedRowIndex === index) {
      // Annuler la sélection si l'utilisateur clique à nouveau sur la même ligne
      this.selectedRowIndex = -1;
      this.ligneRow = {}; // Réinitialiser l'objet ligneRow
    } else {
      // Sinon, mettre à jour la ligne sélectionnée et l'objet ligneRow
      this.selectedRowIndex = index;
      this.ligneRow = element;
    }
    this.buttonActionState = 'in';
    console.log("selectedRow--  "+ this.ligneRow)
    console.log("selectedRow as JSON-- ", JSON.stringify(this.ligneRow));
  }

  EditLigne() {
    if (this.ligneRow) {
      console.log("EditLigne--  "+ this.ligneRow)
      console.log("selectedRow as JSON-- ", JSON.stringify(this.ligneRow));
      // Votre logique pour modifier la ligne
    }
  }
  DeleteLigne() {
    if (this.ligneRow) {
      console.log("DeleteLigne--  "+ this.ligneRow)
      //console.log("selectedRow as JSON-- ", JSON.stringify(this.ligneRow));
      // Votre logique pour supprimer la ligne
    }
  }
  isObjectEmpty(obj: any): boolean {
    return Object.keys(obj).length === 0;
  }
  //zoom table
  zoomLevel: number = 1;
  zoomIn():void {
    this.zoomLevel += 0.1;  // Augmente de 10%
  }
  zoomInit():void {
    this.zoomLevel = 1;  // Augmente de 10%
  }
  zoomOut():void {
    if (this.zoomLevel > 0.1) {  // On ne peut pas avoir un zoomLevel inférieur à 0.1 (10%)
      this.zoomLevel -= 0.1;  // Diminue de 10%
    }
  }

  //Forme Add Edit
  openAddEditLignForm() {
    const dialogRef = this._dialog.open(LigneAddEditComponent, {});
    dialogRef.afterClosed().subscribe({
      next: (val) => {
        if (val) {
          //this.getUtilisateurs();
        }
      },
    });
    //this.getListLignes();
  }


  //fonctions
  getLignesTelephonique(): void {
    this.ligneService.getAllLignes().subscribe(
      (data: any[]):void => {
        //this.typesLigne = data;
        console.log(data)
        this.dataSource = new MatTableDataSource(data);
      },
      (error):void => {
        this._coreService.openSnackBar('Erreur lors de la récupération des types de ligne:'+ error.error.message);
        console.error(error.error.message);
      }
    );
  }

  getAttributNames(): void {
    this.attributService.getAllAttributNames().subscribe(
      (data: string[]): void => {
        if (data && data.length > 0) {
          this.attributNames = data;
          this.displayedColumns = [...this.displayedColumns, ...this.attributNames];
        } else {
          // Gérer le cas où data est vide ou undefined
        }
        console.log("getAttributNames...  "+data);
        console.log("this.displayedColumns...  "+this.displayedColumns);
      },
      (error): void => {
        this._coreService.openSnackBar('Erreur lors de la récupération des noms des attributs:' + error.error.message);
        console.error(error.error.message);
      }
    );
  }
  findAttributValue(ligneAttributs: any[], attributName: string): string {
    const attributObj = ligneAttributs.find(attr => attr.attribut.nomAttribut === attributName);
    return attributObj ? attributObj.valeurAttribut : 'N/A';
  }
}
