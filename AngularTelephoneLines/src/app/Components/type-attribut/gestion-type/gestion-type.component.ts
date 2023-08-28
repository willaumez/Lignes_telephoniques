import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Attribut, TypeVariable} from "../../../Models/Attribut";
import {TypeLigne} from "../../../Models/TypeLigne";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {TypeAttributService} from "../../../Services/type-attribut.service";
import {CoreService} from "../../../core/core.service";
import {MatChipInputEvent} from "@angular/material/chips";

export interface Section2 {
  name: string;
  updated: Date;
}

@Component({
  selector: 'app-gestion-type',
  templateUrl: './gestion-type.component.html',
  styleUrls: ['./gestion-type.component.scss']
})
export class GestionTypeComponent implements OnInit {
  errorMessage!: string;
  selectAdd: boolean = false;
  selectUpdate: boolean = false;
  typeLigneForm!: FormGroup;

  typeAttributs: Set<Attribut> = new Set<Attribut>();

  attributs: Attribut[] = [];

  @Input()
  dataSource!: MatTableDataSource<any>;
  displayedColumns: string[] = ['idType', 'nomType', 'createdDate', 'descriptionType', 'attributs', 'ACTIONS'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private _fb: FormBuilder, private typeAttributService: TypeAttributService, private _coreService: CoreService
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
    this.getTypesLigne();
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
    this.typeAttributService.deleteTypeLigne(idType).subscribe(
      (response): void => {
        this.getTypesLigne();
        this._coreService.openSnackBar("Type et ligne téléphonique supprimés avec succès !");
      },
      (error) => {
        this._coreService.openSnackBar(error.error.message);
      }
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

    this.getAttributs();
    this.selectUpdate = true;
  }

  handleResetUpdate() {
    this.selectUpdate = false;
  }


  //Fonctions
  getTypesLigne(): void {
    this.typeAttributService.getAllTypesLigne().subscribe({
        next: (data: any[]): void => {
          this.dataSource = new MatTableDataSource(data);
          this.dataSource.sort = this.sort;
          this.dataSource.paginator = this.paginator;
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
      const formData = this.typeLigneForm.value;
      this.typeAttributService.updateTypeLigne(formData).subscribe({
          next: (response): void => {
            this._coreService.openSnackBar("Type de ligne enregistré avec succès !");
            this.getTypesLigne();
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
  applyFilter(event: Event): void {
    this.selectAdd = false;
    this.selectUpdate = false;
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  edit(enumValue: string, newValue: string): void {
    const enumeration = this.typeLigneForm.get('enumeration')!.value;
    const index = enumeration.indexOf(enumValue);
    if (index >= 0 && newValue.trim()) {
      enumeration[index] = newValue.trim();
    }
  }


}
