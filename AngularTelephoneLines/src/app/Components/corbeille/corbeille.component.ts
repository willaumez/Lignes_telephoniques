import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {CoreService} from "../../core/core.service";
import {CorbeilleService} from "../../Services/corbeille.service";
import {PagedResponse, RestoreResponse} from "../../Models/PagedResponse";
import {Corbeille} from "../../Models/Corbeille";
import {MatSort} from "@angular/material/sort";

@Component({
  selector: 'app-corbeille',
  templateUrl: './corbeille.component.html',
  styleUrls: ['./corbeille.component.scss']
})
export class CorbeilleComponent implements OnInit {
  //pagination
  pageSizeOptions: number[] = [10, 23, 33, 50, 100,500];
  pageSize!: number;
  currentPage: number = 0;
  totalPages!: number;
  totalItems!: number;
  keyword: string = "";

  errorMessage!: string;

  isLoading: boolean = false;

  //pointer
  isDownload: boolean = false;

  displayedColumns = [ 'idCorbeille', 'nomType', 'numeroLigne', 'dateSuppression', 'numeroSerie', 'etat', 'affectation', 'poste', 'dateLivraison', 'montant', 'ACTIONS'];
  @Input()
  dataSource!: MatTableDataSource<any>;

  @ViewChild(MatSort) sort!: MatSort;

  @ViewChild('input', { static: false }) input!: ElementRef<HTMLInputElement>;
  // Déclarez la variable selectedDate
  selectedDate!: Date | null;

  constructor(private corbeilleService: CorbeilleService, private _coreService: CoreService) {
  }
  ngOnInit(): void {
    this.pageSize = this.pageSizeOptions[0];
    this.getCorbeille();
  }

  getCorbeille(): void {
    this.isDownload = true;
    this.corbeilleService.listCorbeille(this.currentPage, this.pageSize, this.keyword).subscribe({
      next: (data: PagedResponse<Corbeille>): void => {
        // Utilisez data.users car "users" est un champ dans PagedResponse<User>
        this.dataSource = new MatTableDataSource(data.dataElements);

        // Si vous souhaitez utiliser les métadonnées pour d'autres choses
        this.currentPage = data.currentPage;
        this.totalItems = data.totalItems;
        this.totalPages = data.totalPages;
        this.isDownload = false;
        //console.log(JSON.stringify(data, null, 2));
      },
      error: err => {
        this.errorMessage = ('Erreur lors de la récupération des éléments supprimés: '+err.error.message)
        this._coreService.openSnackBar('Erreur lors de la récupération des éléments supprimés:! \n ');
        this.isDownload = false;
      }
    });
  }

  applyFilter(event: Event) {
    // Récupérez la valeur du champ de filtre
    const filterValue = (event.target as HTMLInputElement).value;
    this.pageSize = this.pageSizeOptions[0];
    this.currentPage = 0;
    // Affectez la valeur à la variable keyword
    this.keyword = filterValue.trim();
    this.getCorbeille();
  }
  applyDateFilter(selectedDate: Date | null): void {
    if (selectedDate) {
      const formattedDate: string = this.formatDate(selectedDate);
      this.selectedDate = selectedDate;
      // Appliquer le filtre en utilisant la date formatée
      this.pageSize = this.pageSizeOptions[0];
      this.currentPage = 0;
      this.keyword = formattedDate;
      this.getCorbeille();
    }
  }

  formatDate(date: Date): string {
    const year: number = date.getFullYear();
    const month: string = (date.getMonth() + 1).toString().padStart(2, '0');
    const day: string = date.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
  }
  resetDateField(): void {
    // Effacer la valeur du champ de date en réinitialisant le modèle
    this.selectedDate = null;
    this.input.nativeElement.value = '';
    this.dataSource.filter = '';
    this.pageSize = 10;
    this.currentPage = 0;
    this.keyword = "";
    this.onDataChanged();
  }

  // delete
  handleDelete(id: number) {
    let conf:boolean = confirm("Êtes-vous certain de vouloir le supprimer?")
    if (!conf) return;
    this.isLoading = true;
    this.corbeilleService.deleteCorbeille(id).subscribe({
      next: (res):void => {
        this.isLoading = false;
        this.getCorbeille();
        this._coreService.openSnackBar("ligne supprimé avec succès! ");
      },
      error:err => {
        this.isLoading = false;
        this._coreService.openSnackBar("Erreur: "+err.error?.message);
      }
    });
  }
  handleRestoration(idCorbeille: number) {
    let conf:boolean = confirm("Êtes-vous certain de vouloir Restaurer cet élément?")
    if (!conf) return;
    this.isLoading = true;
    this.corbeilleService.restorCorbeille(idCorbeille).subscribe({
      next: (res):void => {
        this.isLoading = false;
        this.getCorbeille();
        this._coreService.openSnackBar("ligne restauré avec succès! ");
      },
      error:err => {
        this.isLoading = false;
        this._coreService.openSnackBar("Erreur: "+err.error?.message);
      }
    });
  }

  handleResetAll():void {
    let conf:boolean = confirm("Etes-vous certain de supprimer tout les éléments corbeille ?")
    if (!conf) return;
    this.isLoading = true;
    this.corbeilleService.deleteAllCorbeille().subscribe({
      next: (res):void => {
        this.isLoading = false;
        this.getCorbeille();
        this._coreService.openSnackBar("Corbeille supprimé avec succès! ");
      },
      error:err => {
        this.isLoading = false;
        this._coreService.openSnackBar("Erreur en supprimant la corbeille ! "+err.error.message);
      }
    });
  }

  handleRestorationAll() {
    let conf:boolean = confirm("Êtes-vous certain de vouloir Restaurer toute les lignes ?")
    if (!conf) return;
    this.isLoading = true;
    this.corbeilleService.restorCorbeilleAll().subscribe({
      next: (res: RestoreResponse):void => {
        //console.log(JSON.stringify(res, null, 2));
        this.isLoading = false;
        if (res.restoredCount>0){
          this._coreService.openSnackBar("Nombre de lignes restauré avec succès! = "+ res.restoredCount);
        }
        setTimeout(() => {
          if (res.notRestoredCount > 0) {
            this._coreService.openSnackBar("Nombre de lignes non-restauré = " + res.notRestoredCount);
          }
        }, 2000);

        this.getCorbeille();
      },
      error:err => {
        this.isLoading = false;
        //console.log(JSON.stringify(err, null, 2));
        this._coreService.openSnackBar("Erreur! "+err.error?.message);
      }
    });
  }



  //Pagination
  firstPage() {
    this.currentPage = 0;
    this.onDataChanged();
  }

  previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.onDataChanged();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.onDataChanged();
    }
  }

  lastPage() {
    this.currentPage = this.totalPages - 1;
    this.onDataChanged();
  }
  onDataChanged() {
    this.getCorbeille();
  }
  changePageSize(event: Event) {
    const selectElement = event.target as HTMLSelectElement;
    const newSize = selectElement.value;
    this.pageSize = +newSize;  // Convertir la chaîne en nombre
    this.firstPage();
    this.onDataChanged();
  }




}
