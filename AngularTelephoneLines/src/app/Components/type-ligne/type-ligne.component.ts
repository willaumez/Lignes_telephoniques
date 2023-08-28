import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatDialog} from "@angular/material/dialog";
import {CoreService} from "../../core/core.service";
import {LigneTelephoniqueService} from "../../Services/ligne-telephonique.service";
import {TypeAttributService} from "../../Services/type-attribut.service";
import {LigneTelephonique} from "../../Models/LigneTelephonique";
import {LigneAddEditComponent} from "../lignes-telephonique/ligne-add-edit/ligne-add-edit.component";
import {TypeLigne} from "../../Models/TypeLigne";
import {Attribut} from "../../Models/Attribut";

@Component({
  selector: 'app-type-ligne',
  templateUrl: './type-ligne.component.html',
  styleUrls: ['./type-ligne.component.scss']
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
    //this.getTypesLigne();
    this.getLignesTelephonique();
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
      const data: any = this.ligneRow;
      const dialogRef = this._dialog.open(LigneAddEditComponent, {
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

  //Forme Add Edit
  openAddEditLignForm() {
    const dialogRef = this._dialog.open(LigneAddEditComponent, {});
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


  //fonctions
  getAllTypesLignes(): void {
    this.typeAttributService.getAllTypesLigne().subscribe({
        next: (data: any[]): void => {
          this.typeLignes = data;
          console.log("getAllTypesLignes--  "+JSON.stringify(this.typeLignes))
        },
        error: (error) => {
          this.errorMessage = ('Erreur lors de la récupération des types de ligne: ' + error.error.message)
          this._coreService.openSnackBar('Erreur lors de la récupération des types de ligne:' + error.error.message);
        }
      }
    );
  }
  getTypesLigne(): void {
    this.typeAttributService.getTypeLigne(1).subscribe({
        next: (data: TypeLigne): void => {
          this.selectedTypeLigne = data;
          this.getAttributNames();
        },
        error: (error) => {
          this.errorMessage = ('Erreur lors de la récupération des types de ligne: ' + error.error.message)
          this._coreService.openSnackBar('Erreur lors de la récupération des types de ligne:' + error.error.message);
        }
      }
    );
  }
  // Méthode pour définir le type de ligne sélectionné
  selectTypeLigne(typeLigne: TypeLigne): void {
    this.selectedTypeLigne = typeLigne;
    console.log("selectTypeLigne-- "+JSON.stringify(this.selectedTypeLigne));
    this.getAttributNames();
  }
  getLignesTelephonique(): void {
    this.getAttributNames();
    this.ligneService.getAllLignes().subscribe({
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



}
