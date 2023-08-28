import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Attribut, TypeVariable} from "../../../Models/Attribut";
import {TypeLigne} from "../../../Models/TypeLigne";
import {Section2} from "../gestion-type/gestion-type.component";
import {MatChipInputEvent} from "@angular/material/chips";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {TypeAttributService} from "../../../Services/type-attribut.service";
import {CoreService} from "../../../core/core.service";
import {Observable} from "rxjs";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatMenuTrigger} from "@angular/material/menu";

@Component({
  selector: 'app-gestion-attribut',
  templateUrl: './gestion-attribut.component.html',
  styleUrls: ['./gestion-attribut.component.scss']
})
export class GestionAttributComponent implements OnInit {
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
  displayedColumns: string[] = ['idAttribut', 'nomAttribut', 'type', 'valeurAttribut', 'enumeration', 'ACTIONS'];
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
    this.typeAttributService.deleteAttribut(idAttribut).subscribe({
        next: (responce): void => {
          this.getAttributs();
          this._coreService.openSnackBar("Attribut supprimé avec succès !");
        },
        error: (error) => {
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
    this.typeAttributService.getAllAttributs().subscribe({
      next: (data: any[]) => {
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      },
      error: (error) => {
        this.errorMessage = ('Erreur lors de la récupération des attributs: ' + error.error.message)
        this._coreService.openSnackBar('Erreur lors de la récupération des attributs:');
      }
    });
  }

  onAttributSubmit(): void {
    if (this.attributForm.valid) {
      const formData = this.attributForm.value;

      this.typeAttributService.saveAttribut(formData).subscribe({
          next: (data: any) => {
            this._coreService.openSnackBar("Attribut enregistré avec succès !");
            this.getAttributs();
            this.handleAdd();
          },
          error: (error) => {
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
  applyFilter(event: Event): void {
    this.selectAdd = false;
    this.selectUpdate = false;
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
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


}
