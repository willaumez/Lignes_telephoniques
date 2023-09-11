import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {MatDialog} from "@angular/material/dialog";
import {LigneAddEditComponent} from "./ligne-add-edit/ligne-add-edit.component";
import {CoreService} from "../../core/core.service";
import {LigneTelephoniqueService} from "../../Services/ligne-telephonique.service";
import {TypeAttributService} from "../../Services/type-attribut.service";
import {MatTableDataSource} from "@angular/material/table";
import {LigneTelephonique} from "../../Models/LigneTelephonique";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {PagedResponse} from "../../Models/PagedResponse";

@Component({
  selector: 'app-lignes-telephonique',
  templateUrl: './lignes-telephonique.component.html',
  styleUrls: ['./lignes-telephonique.component.scss'],
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
export class LignesTelephoniqueComponent implements OnInit {
  // Initialisation des variables
  pageSizeOptions: number[] = [12, 33, 50, 100, 300,500];
  pageSize!: number;
  currentPage: number = 0;
  totalPages!: number;
  totalItems!: number;
  keyword: string = "";

  //pointer
  isDownload: boolean = false;


  errorMessage!: string;
  columnsInit: string[] = ['idLigne', 'typeLigne.nomType', 'numeroLigne', 'affectation', 'poste', 'etat', 'dateLivraison', 'numeroSerie', 'montant', 'createdDate'];
  displayedColumns: string[] = [];
  attributNames: string[] = [];

  @Input()
  dataSource!: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private _dialog: MatDialog, private _coreService: CoreService, private ligneService: LigneTelephoniqueService,
              private attributService: TypeAttributService) {
  }

  ngOnInit(): void {
    this.pageSize = this.pageSizeOptions[0];
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
          this.getLignesTelephonique();
        }
      },
    });
  }


  //fonctions
  getLignesTelephonique(): void {
    this.isDownload = true;
    this.selectedRowIndex = -1;
    this.ligneRow = {} as LigneTelephonique;
    this.getAttributNames();
    this.ligneService.getAllLignes(this.currentPage, this.pageSize, this.keyword).subscribe({
        next: (data: PagedResponse<LigneTelephonique>): void => {
          this.dataSource = new MatTableDataSource(data.dataElements);
          // Initialiser le paginator et le sort ici
          this.dataSource.sort = this.sort;
          this.currentPage = data.currentPage;
          this.totalItems = data.totalItems;
          this.totalPages = data.totalPages;

          this.isDownload = false;
          //console.log(JSON.stringify(data, null, 2));
        },
        error: (error) => {
          this.errorMessage = "Erreur lors de la récupération des types de ligne";
          this._coreService.openSnackBar('Erreur lors de la récupération des Noms de types de ligne:' + error.error.message);
          this.isDownload = false;
        }
      }
    );
  }
  getAttributNames(): void {
    this.attributService.getAllAttributNames().subscribe({
        next: (data: string[]): void => {
          if (data && data.length > 0) {
            this.attributNames = [];
            this.displayedColumns = [];
            this.attributNames = data;
            this.displayedColumns = [...this.columnsInit, ...this.attributNames];
          }
        },
        error: (error) => {
          this._coreService.openSnackBar('Erreur lors de la récupération des noms des attributs:');
          this.errorMessage = 'Erreur lors de la récupération des noms des attributs:' + error.error.message;
        }
      }
    );
  }

  findAttributValue(ligneAttributs: any[], attributName: string): string {
    const attributObj = ligneAttributs.find(attr => attr.attribut.nomAttribut === attributName);
    return attributObj ? attributObj.valeurAttribut : '   ';
  }

  // Pour le filtre
  applyFilter(event: Event) {
    // Récupérez la valeur du champ de filtre
    const filterValue = (event.target as HTMLInputElement).value;
    this.pageSize = this.pageSizeOptions[0];
    this.currentPage = 0;
    // Affectez la valeur à la variable keyword
    this.keyword = filterValue.trim();
    this.getLignesTelephonique();
  }

  //pagination
  firstPage():void {
    this.currentPage = 0;
    this.onDataChanged();
  }
  previousPage():void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.onDataChanged();
    }
  }
  nextPage():void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.onDataChanged();
    }
  }
  lastPage() {
    this.currentPage = this.totalPages - 1;
    this.onDataChanged();
  }
  // Méthode pour rafraîchir les données
  onDataChanged():void {
    this.getLignesTelephonique();
  }
  changePageSize(event: Event):void {
    const selectElement = event.target as HTMLSelectElement;
    const newSize = selectElement.value;
    this.pageSize = +newSize;  // Convertir la chaîne en nombre
    this.firstPage();
    this.onDataChanged();
  }


  //format numero
  formatNumeroLigne(numeroLigne: string): string {
    if (!numeroLigne) return '---';

    const cleanedNumber = numeroLigne.replace(/[^\d]/g, '');

    if (cleanedNumber.length === 12) {
      return `(${cleanedNumber.substr(0, 3)}) ${cleanedNumber.substr(3, 3)} ${cleanedNumber.substr(6, 3)} ${cleanedNumber.substr(9, 3)}`;
    }
    return numeroLigne;
  }





}
