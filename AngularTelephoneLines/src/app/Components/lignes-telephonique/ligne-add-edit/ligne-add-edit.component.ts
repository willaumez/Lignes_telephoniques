import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {TypeAttributService} from "../../../Services/type-attribut.service";
import {CoreService} from "../../../core/core.service";
import {Attribut} from "../../../Models/Attribut";
import {TypeLigne} from "../../../Models/TypeLigne";
import {AbstractControl, FormArray, FormBuilder, FormGroup, isFormGroup, Validators} from "@angular/forms";
import {MatSelectChange} from "@angular/material/select";
import {EtatType} from "../../../Models/LigneTelephonique";
import {LigneTelephoniqueService} from "../../../Services/ligne-telephonique.service";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-ligne-add-edit',
  templateUrl: './ligne-add-edit.component.html',
  styleUrls: ['./ligne-add-edit.component.scss']
})
export class LigneAddEditComponent implements OnInit{

  ligneForm: FormGroup;
  etatTypes = Object.values(EtatType);
  typesLigne: TypeLigne[] = [];
  constructor(private typeAttributService: TypeAttributService, private _coreService: CoreService,
              private ligneService: LigneTelephoniqueService, private fb: FormBuilder, private _dialogRef: MatDialogRef<LigneAddEditComponent> ) {
    this.ligneForm = this.fb.group({
      idLigne: [''],
      numeroLigne: ['', Validators.required],
      affectation: [''],
      poste: [''],
      etat: ['', Validators.required],
      dateLivraison: [''],
      numeroSerie: [''],
      montant: [''],
      createdDate: [''],
      typeLigne: this.fb.group({
        idType: ['', Validators.required],
        nomType: [''],
        descriptionType: [''],
        createdDate: [''],
        attributs: this.fb.array([])
      }),
    });
  }

  ngOnInit(): void {
    this.getTypesLigne();
  }

  //get Type Ligne
  getTypesLigne(): void {
    this.typeAttributService.getAllTypesLigne().subscribe(
      (data: any[]):void => {
        this.typesLigne = data;
        console.log(this.typesLigne)
      },
      (error):void => {
        this._coreService.openSnackBar('Erreur lors de la récupération des types de ligne:'+ error.error.message);
        console.error(error.error.message);
      }
    );
  }
  get attributs() {
    return (this.ligneForm.get('typeLigne.attributs') as FormArray);
  }

  onTypeChange(event: MatSelectChange) {
    const typeId = event.value;
    const selectedType = this.typesLigne.find(t => t.idType === typeId);
    const attributsArray = this.ligneForm.get('typeLigne.attributs') as FormArray;
    if (selectedType && selectedType.attributs) {
      attributsArray.clear();
      selectedType.attributs.forEach(attr => {
        const attrGroup = this.fb.group({
          idAttribut: [attr.idAttribut, Validators.required],
          nomAttribut: [attr.nomAttribut, Validators.required],
          type: [attr.type, Validators.required],
          valeurAttribut: [''],
          enumeration: [attr.enumeration]
        });
        // Mettre à jour les validateurs pour 'valeurAttribut' en fonction de la présence d'une énumération
        if (attr.enumeration && attr.enumeration.length > 0) {
          attrGroup.get('valeurAttribut')?.setValidators([Validators.required]);
        } else {
          attrGroup.get('valeurAttribut')?.setValidators(null);
        }
        attrGroup.get('valeurAttribut')?.updateValueAndValidity();

        attributsArray.push(attrGroup);
      });
    }

  }
  onFormSubmit() {
    console.log(this.ligneForm.value);
    if (this.ligneForm.valid) {
      const formData = this.ligneForm.value;
      this.ligneService.saveLigneTelephonique(formData).subscribe(
        (response):void => {
          this._coreService.openSnackBar("Ligne téléphonique enregistré avec succès !");
          this._dialogRef.close(true);
        },
        (error) => {
          this._coreService.openSnackBar(error.error.message);
          console.log(error)
        }
      );
    }
    // Votre logique pour envoyer les données
  }
  isFormGroup(control: AbstractControl): control is FormGroup {
    return control instanceof FormGroup;
  }
  isFormValid() {
    return this.ligneForm.valid && this.ligneForm.get('typeLigne')?.get('attributs')?.valid;
  }
  isEmptyEnumeration(enumeration: any): boolean {
    if (enumeration === null) {
    } else if (enumeration === undefined) {
    } else if (Array.isArray(enumeration) && enumeration.length === 0) {
    } else {
    }
    return !enumeration || (Array.isArray(enumeration) && enumeration.length === 0);
  }
  startDate() {
    //return (new Date(2023, 0, 1));
    return new Date(Date.now());
  }
}
