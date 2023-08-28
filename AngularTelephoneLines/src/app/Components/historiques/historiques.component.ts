import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {CoreService} from "../../core/core.service";
import {MatTableDataSource} from "@angular/material/table";
import {HistoriqueService} from "../../Services/historique.service";
import {Historique} from "../../Models/Historique";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {UserService} from "../../Services/user.service";
import {LoginService} from "../../Services/login.service";

@Component({
  selector: 'app-historiques',
  templateUrl: './historiques.component.html',
  styleUrls: ['./historiques.component.scss']
})
export class HistoriquesComponent implements OnInit {
  errorMessage!: string;
  displayedColumns: string[] = [
    'idHistorique', 'actionEffectue', 'dateAction', 'nomOperateur', 'elementCible', 'ACTIONS'];

  @Input()
  dataSource!: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  @ViewChild('input', { static: false }) input!: ElementRef<HTMLInputElement>;
  // Déclarez la variable selectedDate
  selectedDate!: Date | null;

  constructor(private historiqueService: HistoriqueService, private _coreService: CoreService, private loginService: LoginService) { }

  ngOnInit(): void {
    this.getHistoriques();
  }

  getHistoriques(): void {
    this.historiqueService.listHistoriques().subscribe({
      next: (data: Historique[]): void => {
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      },
      error: err => {
        this.errorMessage = ('Erreur lors de la récupération des types de ligne: '+err.error.message)
        this._coreService.openSnackBar('Erreur lors de la récupération des historique:! \n ', err);
      }
    });
  }

  applyFilter(event: Event):void {
    const filterValue:string = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  applyDateFilter(selectedDate: Date | null): void {
    if (selectedDate) {
      const formattedDate: string = this.formatDate(selectedDate);
        this.selectedDate = selectedDate;
      // Appliquer le filtre en utilisant la date formatée
      this.dataSource.filter = formattedDate.trim().toLowerCase();

      if (this.dataSource.paginator) {
        this.dataSource.paginator.firstPage();
      }
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

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }



  // delete
  handleDelete(id: number) {
    let conf:boolean = confirm("Es-tu sûre de supprimer cet historique ?")
    if (!conf) return;
    this.historiqueService.deleteHistorique(id).subscribe({
      next: (res):void => {
        this.getHistoriques();
        this._coreService.openSnackBar("historique supprimé avec succès! ");
      },
      error:err => {
        this._coreService.openSnackBar("historique Innexistant! ");
        console.log(err);
      }
    });
  }


  handleResetAll():void {
    let conf:boolean = confirm("Etes-vous certain de supprimer tout l'historique ?")
    if (!conf) return;
    this.historiqueService.deleteAllHistorique(this.loginService.getUserData().username).subscribe({
      next: (res):void => {
        this.getHistoriques();
        this._coreService.openSnackBar("historiques supprimé avec succès! ");
      },
      error:err => {
        this._coreService.openSnackBar("Erreur en supprimant les historiques ! ");
        console.log(err);
      }
    });

  }
}
