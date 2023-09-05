import {ChangeDetectorRef, Component, Input, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Attribut, TypeVariable} from "../../../Models/Attribut";
import {TypeLigne} from "../../../Models/TypeLigne";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {TypeAttributService} from "../../../Services/type-attribut.service";
import {CoreService} from "../../../core/core.service";
import {PagedResponse} from "../../../Models/PagedResponse";


@Component({
  selector: 'app-gestion-type',
  templateUrl: './gestion-type.component.html',
  styleUrls: ['./gestion-type.component.scss']
})
export class GestionTypeComponent implements OnInit {
  pageSizeOptions: number[] = [10, 23, 33, 50, 100];
  pageSize!: number;
  currentPage: number = 0;
  totalPages!: number;
  totalItems!: number;
  keyword: string = "";

  errorMessage!: string;
  selectAdd: boolean = false;
  selectUpdate: boolean = false;
  typeLigneForm!: FormGroup;

  isLoading: boolean = false;

  typeAttributs: Set<Attribut> = new Set<Attribut>();


  attributs: Attribut[] = [];

  @Input()
  dataSource!: MatTableDataSource<any>;
  displayedColumns: string[] = ['idType','nomType', 'createdDate', 'descriptionType', 'attributs', 'ACTIONS'];
  @ViewChild(MatSort) sort!: MatSort;

  public selectedAttributsIds: number[] = [];
  constructor(private _fb: FormBuilder, private typeAttributService: TypeAttributService, private _coreService: CoreService,
              private cdRef:ChangeDetectorRef
  ) {
    this.typeLigneForm = this._fb.group({
      idType: [null],
      nomType: ['', Validators.required],
      descriptionType: ['', Validators.maxLength(200)],
      createdDate: [null],
      attributs: new FormControl([]),
    });
    this.dataSource = new MatTableDataSource<TypeLigne>();
  }

  ngOnInit(): void {
    this.pageSize = this.pageSizeOptions[0];
    this.getTypesLigne();
    this.cdRef.detectChanges();
  }

  //handle
  handleAdd(): void {
    this.selectAdd = !this.selectAdd;
    this.handleResetUpdate()
    this.typeLigneForm.reset({
      idType: null,
      nomType: '',
      descriptionType: '',
      createdDate: null,
      attributs: []
    });
    this.getAttributs();
  }

  handleDelete(idType: number): void {
    let conf: boolean = confirm("Êtes-vous sûr de supprimer ce type de ligne ?")
    if (!conf) return;
    let confirmation: boolean = confirm("Supprimer aussi toutes les lignes téléphonique du type .")
    if (!confirmation) return;
    this.isLoading = true;
    this.typeAttributService.deleteTypeLigne(idType).subscribe({
      next: (response): void => {
        this.isLoading = false;
        this.getTypesLigne();
        this._coreService.openSnackBar("Type et ligne téléphonique supprimés avec succès !");
      },
      error: (error) => {
        this.isLoading = false;
        this._coreService.openSnackBar(error.error.message);
      }}
    );
  }

  handleEdit(typeLigne: TypeLigne): void {
    this.typeLigneForm.patchValue({
      idType: typeLigne.idType,
      nomType: typeLigne.nomType,
      descriptionType: typeLigne.descriptionType,
      createdDate: typeLigne.createdDate || null,
      attributs: typeLigne.attributs
    });
    this.selectedAttributsIds = [];
    typeLigne.attributs.forEach(attr => {
      if (attr.idAttribut !== undefined) {
        this.selectedAttributsIds.push(attr.idAttribut);
      }
    });
    this.getAttributs();
    this.selectUpdate = true;
  }

  handleResetUpdate() {
    this.selectUpdate = false;
  }


  //Fonctions
  getTypesLigne(): void {
    this.typeAttributService.getTypesLignePage(this.currentPage, this.pageSize, this.keyword).subscribe({
        next: (data: PagedResponse<TypeLigne>): void => {
          this.dataSource = new MatTableDataSource(data.dataElements);
          this.currentPage = data.currentPage;
          this.totalItems = data.totalItems;
          this.totalPages = data.totalPages;
          //console.log(JSON.stringify(data, null, 2));
        },
        error: (error) => {
          this.errorMessage = ('Erreur lors de la récupération des types de ligne: ' + error.error.message)
          this._coreService.openSnackBar('Erreur lors de la récupération des types de ligne:' + error.error.message);
        }
      }
    );
  }

  getAttributs(): void {
    this.typeAttributService.getAllAttributs().subscribe({
        next: (data: any[]): void => {
          this.attributs = data;
        },
        error: (error) => {
          this._coreService.openSnackBar('Erreur lors de la récupération des attributs:');
          console.error(error.error.message);
        }
      }
    );
  }

  onTypeLigneSubmit(): void {
    if (this.typeLigneForm.valid) {
      const formData = this.typeLigneForm.value;
      this.typeAttributService.saveTypeLigne(formData).subscribe({
          next: (response): void => {
            this._coreService.openSnackBar("Type de ligne enregistré avec succès !");
            this.getTypesLigne();
            this.handleAdd();
          },
          error: (error) => {
            this.handleAdd();
            this._coreService.openSnackBar(error.error.message);
          }
        }
      );
    }
  }

  onTypeLigneUpdate(): void {
    if (this.typeLigneForm.valid) {
      this.isLoading = true;
      const formData = this.typeLigneForm.value;
      this.typeAttributService.updateTypeLigne(formData).subscribe({
          next: (response): void => {
            this.isLoading= false;
            this._coreService.openSnackBar("Type de ligne enregistré avec succès !");
            this.getTypesLigne();
            this.handleResetUpdate();
          },
          error: (error) => {
            this.isLoading= false;
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
    this.getTypesLigne();
  }

  edit(enumValue: string, newValue: string): void {
    const enumeration = this.typeLigneForm.get('enumeration')!.value;
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
    this.getTypesLigne();
  }
  changePageSize(event: Event) {
    const selectElement = event.target as HTMLSelectElement;
    const newSize = selectElement.value;
    this.pageSize = +newSize;
    this.firstPage();
    this.onDataChanged();
  }




}
