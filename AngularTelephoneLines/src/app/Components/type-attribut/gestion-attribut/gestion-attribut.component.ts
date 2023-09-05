import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Attribut, TypeVariable} from "../../../Models/Attribut";
import {MatChipInputEvent} from "@angular/material/chips";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {TypeAttributService} from "../../../Services/type-attribut.service";
import {CoreService} from "../../../core/core.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {PagedResponse} from "../../../Models/PagedResponse";

@Component({
  selector: 'app-gestion-attribut',
  templateUrl: './gestion-attribut.component.html',
  styleUrls: ['./gestion-attribut.component.scss']
})
export class GestionAttributComponent implements OnInit {
  pageSizeOptions: number[] = [10, 23, 33, 50, 100];
  pageSize!: number;
  currentPage: number = 0;
  totalPages!: number;
  totalItems!: number;
  keyword: string = "";

  isLoading: boolean = false;

  errorMessage!: string;
  addOnBlur: boolean = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  selectAdd: boolean = false;
  selectUpdate: boolean = false;
  attributForm!: FormGroup;
  typeVariables: string[] = Object.values(TypeVariable);

  enumerations: Set<string> = new Set<string>();

  //attributs$!: Observable<Attribut[]>;
  @Input()
  dataSource!: MatTableDataSource<any>;
  displayedColumns: string[] = ['idAttribut','nomAttribut', 'type', 'valeurAttribut', 'enumeration', 'ACTIONS'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private _fb: FormBuilder, private _announcer: LiveAnnouncer,
              private typeAttributService: TypeAttributService, private _coreService: CoreService
  ) {
    this.attributForm = this._fb.group({
      idAttribut: [null],
      nomAttribut: ['', Validators.required],
      type: ['', Validators.required],
      valeurAttribut: [null],
      enumeration: this._fb.array([]),
    });
    this.dataSource = new MatTableDataSource<Attribut>();
  }

  ngOnInit(): void {
    this.pageSize = this.pageSizeOptions[0];
    this.getAttributs();
  }

  //handle
  handleAdd(): void {
    this.selectAdd = !this.selectAdd;
    this.handleResetUpdate()
    this.attributForm.reset({
      idAttribut: null,
      nomAttribut: '',
      type: '',
      valeurAttribut: null,
      enumeration: []
    });
  }

  handleDelete(idAttribut: number): void {
    let conf: boolean = confirm("Es-tu sure de supprimer cet Attribut ?")
    if (!conf) return;
    this.isLoading = true;
    this.typeAttributService.deleteAttribut(idAttribut).subscribe({
        next: (responce): void => {
          this.isLoading= false;
          this.getAttributs();
          this._coreService.openSnackBar("Attribut supprimé avec succès !");
        },
        error: (error) => {
          this.isLoading= false;
          this._coreService.openSnackBar(error.error.message);
        }
      }
    );
  }

  handleEdit(attribut: Attribut): void {
    this.attributForm.patchValue({
      idAttribut: attribut.idAttribut,
      nomAttribut: attribut.nomAttribut,
      type: attribut.type,
      valeurAttribut: attribut.valeurAttribut || null,
      enumeration: []
    });
    this.enumerations = new Set<string>(attribut.enumeration);
    this.selectUpdate = true;
  }

  handleResetUpdate() {
    this.selectUpdate = false;
  }


  //Fonctions
  getAttributs(): void {
    this.typeAttributService.getAllAttributsPage(this.currentPage, this.pageSize, this.keyword).subscribe({
      next: (data: PagedResponse<Attribut>) => {
        this.dataSource = new MatTableDataSource(data.dataElements);
        this.currentPage = data.currentPage;
        this.totalItems = data.totalItems;
        this.totalPages = data.totalPages;
        //console.log(JSON.stringify(data, null, 2));
      },
      error: (error) => {
        this.errorMessage = ('Erreur lors de la récupération des attributs: ' + error.error.message)
        this._coreService.openSnackBar('Erreur lors de la récupération des attributs:');
      }
    });
  }

  onAttributSubmit(): void {
    if (this.attributForm.valid) {
      this.isLoading= true;
      const formData = this.attributForm.value;
      this.typeAttributService.saveAttribut(formData).subscribe({
          next: (data: any) => {
            this.isLoading= false;
            this._coreService.openSnackBar("Attribut enregistré avec succès !");
            this.getAttributs();
            this.handleAdd();
          },
          error: (error) => {
            this.isLoading= false;
            this.handleAdd();
            this._coreService.openSnackBar(error.error.message);
            console.log(error)
          }
        }
      );
    }
  }

  onAttributUpdate(): void {
    if (this.attributForm.valid) {
      const formData = this.attributForm.value;
      // Mise à jour des valeurs d'énumération dans le formulaire
      formData.enumeration = Array.from(this.enumerations);
      this.typeAttributService.updateAttribut(formData).subscribe({
          next: (data: any) => {
            this._coreService.openSnackBar("Attribut mis à jour avec succès !");
            this.enumerations = new Set<string>();
            this.getAttributs();
            this.handleResetUpdate();
          },
          error: (error) => {
            this.handleResetUpdate();
            this._coreService.openSnackBar(error.error.message);
          }
        }
      );
    }
  }


  //Recherche
  applyFilter(event: Event) {
    // Récupérez la valeur du champ de filtre
    const filterValue = (event.target as HTMLInputElement).value;
    this.pageSize = this.pageSizeOptions[0];
    this.currentPage = 0;
    // Affectez la valeur à la variable keyword
    this.keyword = filterValue.trim();
    this.getAttributs();
  }


  //enum
  addEnum(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    // Ajouter un nouvel élément à la liste d'énumération
    if (value) {
      if (this.selectUpdate)
        this.enumerations.add(value);
      else this.attributForm.get('enumeration')!.value.push(value);
    }
    // Effacer la valeur de l'input
    event.chipInput!.clear();
  }

  removeEnum(enumValue: string): void {
    const enumeration = this.attributForm.get('enumeration')!.value;
    const index = enumeration.indexOf(enumValue);
    if (this.selectUpdate)
      this.enumerations.delete(enumValue);
    else {
      if (index >= 0) {
        enumeration.splice(index, 1);
        this._announcer.announce(`Removed ${enumValue}`);
      }
    }
  }

  edit(enumValue: string, newValue: string): void {
    const enumeration = this.attributForm.get('enumeration')!.value;
    const index = enumeration.indexOf(enumValue);
    if (index >= 0 && newValue.trim()) {
      enumeration[index] = newValue.trim();
    }
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
    this.getAttributs();
  }
  changePageSize(event: Event) {
    const selectElement = event.target as HTMLSelectElement;
    const newSize = selectElement.value;
    this.pageSize = +newSize;  // Convertir la chaîne en nombre
    this.firstPage();
    this.onDataChanged();
  }




}
