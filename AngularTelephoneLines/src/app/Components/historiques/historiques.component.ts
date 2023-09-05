import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {CoreService} from "../../core/core.service";
import {MatTableDataSource} from "@angular/material/table";
import {HistoriqueService} from "../../Services/historique.service";
import {Historique} from "../../Models/Historique";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {LoginService} from "../../Services/login.service";
import {PagedResponse} from "../../Models/PagedResponse";

@Component({
  selector: 'app-historiques',
  templateUrl: './historiques.component.html',
  styleUrls: ['./historiques.component.scss']
})
export class HistoriquesComponent implements OnInit {
  //pagination
  pageSizeOptions: number[] = [10, 23, 35, 50, 100, 500, 1000];
  pageSize!: number;
  currentPage: number = 0;
  totalPages!: number;
  totalItems!: number;

  keyword: string = "";

  //pointer
  isDownload: boolean = false;
  isLoading: boolean = false;

  errorMessage!: string;
  displayedColumns: string[] = [
    'idHistorique', 'actionEffectue', 'dateAction', 'nomOperateur', 'elementCible', 'ACTIONS'];

  @Input()
  dataSource!: MatTableDataSource<any>;

  @ViewChild(MatSort) sort!: MatSort;

  @ViewChild('input', { static: false }) input!: ElementRef<HTMLInputElement>;
  // Déclarez la variable selectedDate
  selectedDate!: Date | null;

  constructor(private historiqueService: HistoriqueService, private _coreService: CoreService, private loginService: LoginService) { }

  ngOnInit(): void {
    this.pageSize = this.pageSizeOptions[0];
    this.getHistoriques();
  }

  getHistoriques(): void {
    this.isDownload = true;
    this.historiqueService.listHistoriques(this.currentPage, this.pageSize, this.keyword).subscribe({
      next: (data: PagedResponse<Historique>): void => {
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
        this.errorMessage = ('Erreur lors de la récupération des types de ligne: '+err.error.message)
        this._coreService.openSnackBar('Erreur lors de la récupération des historique:! \n ', err);
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
    this.getHistoriques();
  }
  applyDateFilter(selectedDate: Date | null): void {
    if (selectedDate) {
      const formattedDate: string = this.formatDate(selectedDate);
        this.selectedDate = selectedDate;
      // Appliquer le filtre en utilisant la date formatée
      this.pageSize = this.pageSizeOptions[0];
      this.currentPage = 0;
      this.keyword = formattedDate;
      this.getHistoriques();
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
    let conf:boolean = confirm("Es-tu sûre de supprimer cet historique ?")
    if (!conf) return;
    this.isLoading = true;
    this.historiqueService.deleteHistorique(id).subscribe({
      next: (res):void => {
        this.getHistoriques();
        this.isLoading= false;
        this._coreService.openSnackBar("historique supprimé avec succès! ");
      },
      error:err => {
        this.isLoading= false;
        this._coreService.openSnackBar("historique Innexistant! ", err.error.message);
      }
    });
  }


  handleResetAll():void {
    let conf:boolean = confirm("Etes-vous certain de supprimer tout l'historique ?")
    if (!conf) return;
    this.isLoading= true;
    this.historiqueService.deleteAllHistorique(this.loginService.getUserData().username).subscribe({
      next: (res):void => {
        this.getHistoriques();
        this.isLoading = false;
        this._coreService.openSnackBar("historiques supprimé avec succès! ");
      },
      error:err => {
        this.isLoading = false;
        this._coreService.openSnackBar("Erreur en supprimant les historiques ! ");
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
    this.getHistoriques();
  }
  changePageSize(event: Event) {
    const selectElement = event.target as HTMLSelectElement;
    const newSize = selectElement.value;
    this.pageSize = +newSize;  // Convertir la chaîne en nombre
    this.firstPage();
    this.onDataChanged();
  }


}
