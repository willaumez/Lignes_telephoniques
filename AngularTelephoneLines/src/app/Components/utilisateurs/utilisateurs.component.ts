import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatDialog} from "@angular/material/dialog";
import {CoreService} from "../../core/core.service";
import {LoginService} from "../../Services/login.service";
import {UserService} from "../../Services/user.service";
import {UserAddEditComponent} from "./user-add-edit/user-add-edit.component";
import {User} from "../../Models/User";
import {PagedResponse} from "../../Models/PagedResponse";

@Component({
  selector: 'app-utilisateurs',
  templateUrl: './utilisateurs.component.html',
  styleUrls: ['./utilisateurs.component.scss']
})
export class UtilisateursComponent implements OnInit {
  // Initialisation des variables
  pageSizeOptions: number[] = [10, 23, 33, 50, 100];
  pageSize!: number;
  currentPage: number = 0;
  totalPages!: number;
  totalItems!: number;

  keyword: string = "";


  errorMessage!: string;
  displayedColumns: string[] = [
    'username', 'email', 'createdDate', 'role', 'ACTIONS'];
  userData: User = this.loginService.getUserData();

  @Input()
  dataSource!: MatTableDataSource<any>;

/*  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;*/

  constructor(private userService: UserService, private _dialog: MatDialog,
              private _coreService: CoreService, public loginService: LoginService) {
  }

  ngOnInit(): void {
    this.pageSize = this.pageSizeOptions[0];
    this.getUtilisateurs();
  }

  /*getUtilisateurs(): void {
    this.userService.listUsers().subscribe({
        next: (data: any[]): void => {
          this.dataSource = new MatTableDataSource(data);
          /!*this.dataSource.sort = this.sort;
          this.dataSource.paginator = this.paginator;*!/
        },
        error: (error) => {
          this.errorMessage = ('Erreur lors de la récupération des utilisateurs: ' + error)
        }
      }
    );
  }*/

  getUtilisateurs(): void {
    this.userService.listUsers(this.currentPage, this.pageSize, this.keyword).subscribe({
      next: (data: PagedResponse<User>): void => {
        this.dataSource = new MatTableDataSource(data.dataElements);
        this.currentPage = data.currentPage;
        this.totalItems = data.totalItems;
        this.totalPages = data.totalPages;
        //console.log(JSON.stringify(data, null, 2));

      },
      error: (error) => {
        this.errorMessage = ('Erreur lors de la récupération des utilisateurs: ' + error);
        console.log(JSON.stringify(error, null, 2));
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
    this.getUtilisateurs();
  }

  openAddEditLignForm() {
    const dialogRef = this._dialog.open(UserAddEditComponent, {});
    dialogRef.afterClosed().subscribe({
      next: (val) => {
        if (val) {
          this.getUtilisateurs();
        }
      },
    });
    //this.getListLignes();
  }


  // delete
  handleDeleteLigne(id: number) {
    let conf = confirm("Es-tu sure de supprimer cet utilisateur ?")
    if (!conf) return;
    this.userService.deleteUser(id, this.userData.username).subscribe({
      next: (res): void => {
        this.getUtilisateurs();
        this._coreService.openSnackBar("L'utilisateur a été supprimée avec succès! ");
      },
      error: err => {
        this._coreService.openSnackBar("Utilisateur Innexistant! ");
        console.log(err);
      }
    });
  }

  //edit
  openEditForm(data: any) {
    const dialogRef = this._dialog.open(UserAddEditComponent, {
      data,
    });
    dialogRef.afterClosed().subscribe({
      next: (val) => {
        if (val) {
          this.getUtilisateurs();
        }
      },
    });
  }

  //pagination
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
  // Méthode pour rafraîchir les données
  onDataChanged() {
    this.getUtilisateurs();
  }
  changePageSize(event: Event) {
    const selectElement = event.target as HTMLSelectElement;
    const newSize = selectElement.value;
    this.pageSize = +newSize;  // Convertir la chaîne en nombre
    this.firstPage();
    this.onDataChanged();
  }



}
