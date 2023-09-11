import {Component, Inject, OnInit} from '@angular/core';
import {AbstractControl, FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TypeAttributService} from "../../../Services/type-attribut.service";
import {CoreService} from "../../../core/core.service";
import {LigneTelephoniqueService} from "../../../Services/ligne-telephonique.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {LigneAttribut} from "../../../Models/LigneAttributs";
import {Attribut} from "../../../Models/Attribut";
import {EtatType} from "../../../Models/LigneTelephonique";

@Component({
  selector: 'app-type-ligne-add-edit',
  templateUrl: './type-ligne-add-edit.component.html',
  styleUrls: ['./type-ligne-add-edit.component.scss']
})
export class TypeLigneAddEditComponent implements OnInit {
  ligneForm: FormGroup;
  etatTypes = Object.values(EtatType);

  constructor(private typeAttributService: TypeAttributService, private _coreService: CoreService,
              private ligneService: LigneTelephoniqueService, private fb: FormBuilder, private _dialogRef: MatDialogRef<TypeLigneAddEditComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
    this.ligneForm = this.fb.group({
      idLigne: [''],
      typeId: [''],
      numeroLigne: ['', Validators.required],
      affectation: [''],
      poste: [''],
      etat: ['', Validators.required],
      dateLivraison: [''],
      numeroSerie: [''],
      montant: [''],
      createdDate: [''],
      ligneAttributs: this.fb.array([]),
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
    if (this.data?.add) {

      // Initialiser les valeurs de typeLigne à partir de this.data?.typeLigne
      this.ligneForm.get('typeLigne')?.patchValue(this.data?.typeLigne);

      // Gérer les attributs
      const attributsArray = this.ligneForm.get('typeLigne.attributs') as FormArray;
      if (this.data?.typeLigne?.attributs) {
        attributsArray.clear();
        this.data?.typeLigne?.attributs.forEach((attr: Attribut) => {
          const attrGroup = this.fb.group({
            idAttribut: [attr.idAttribut, Validators.required],
            nomAttribut: [attr.nomAttribut, Validators.required],
            type: [attr.type, Validators.required],
            valeurAttribut: [''],
            enumeration: [attr.enumeration]
          });
          // Mettre à jour les validateurs pour 'valeurAttribut'
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
    if (this.data?.edit) {
      this.ligneForm.patchValue(this.data.ligne);
      this.ligneForm.get('typeLigne.idType')?.disable();
      const ligneAttributsArray = this.ligneForm.get('ligneAttributs') as FormArray;
      ligneAttributsArray.clear();
      this.data.ligne.ligneAttributs.forEach((ligneAttr: LigneAttribut) => {
        const ligneAttrGroup = this.fb.group({
          id: [ligneAttr.id, Validators.required], // ID de la ligne attribut
          attribut: this.fb.group({ // Groupe pour les attributs
            idAttribut: [ligneAttr.attribut.idAttribut, Validators.required],
            nomAttribut: [ligneAttr.attribut.nomAttribut],
            type: [ligneAttr.attribut.type],
            enumeration: [ligneAttr.attribut.enumeration]
          }),
          valeurAttribut: [ligneAttr.valeurAttribut] // Valeur de l'attribut pour cette ligne
        });
        ligneAttributsArray.push(ligneAttrGroup);
      });
    }

  }

  onFormSubmit() {
    if (this.ligneForm.valid) {
      const formData = this.ligneForm.value;
      formData.numeroLigne = this.normalizeNumero(formData.numeroLigne);
      if (this.data?.edit) {
        this.ligneService.updateLigneTelephonique(formData).subscribe({
            next: (response): void => {
              this._coreService.openSnackBar("Ligne téléphonique mise à jour avec succès !");
              this._dialogRef.close(true);
            },
            error: (error) => {
              this._coreService.openSnackBar(error.error.message);
              this._dialogRef.close(true);
            }
          }
        );
      } else {
        this.ligneService.saveLigneTelephonique(formData).subscribe({
            next: (response): void => {
              this._coreService.openSnackBar("Ligne téléphonique enregistré avec succès !");
              this._dialogRef.close(true);
            },
            error: (error) => {
              this._coreService.openSnackBar(error.error.message);
              this._dialogRef.close(true);
            }
          }
        );
      }
    }
  }
  private normalizeNumero(numero: string): string {
    if (numero.startsWith("0")) {
      return "212" + numero.substring(1);
    } else if (numero.startsWith("+")) {
      return numero.substring(1);
    } else if (numero.startsWith("7") || numero.startsWith("6")) {
      return "212" + numero;
    } else {
      return numero;
    }
  }
  startDate() {
    //return (new Date(2023, 0, 1));
    return new Date(Date.now());
  }
  get attributs() {
    return (this.ligneForm.get('typeLigne.attributs') as FormArray);
  }// Ajout de la méthode get pour accéder à ligneAttributs
  get ligneAttributs(): FormArray {
    return this.ligneForm.get('ligneAttributs') as FormArray;
  }
  isFormGroup(control: AbstractControl): control is FormGroup {
    return control instanceof FormGroup;
  }
  isFormValid() {
    return this.ligneForm.valid && this.ligneForm.get('typeLigne')?.get('attributs')?.valid;
  }

  isFormLigneAttributValid() {
    return this.ligneForm.valid && this.ligneForm.get('ligneAttributs')?.valid;
  }

  isEmptyEnumeration(enumeration: any): boolean {
    if (enumeration === null) {
    } else if (enumeration === undefined) {
    } else if (Array.isArray(enumeration) && enumeration.length === 0) {
    } else {
    }
    return !enumeration || (Array.isArray(enumeration) && enumeration.length === 0);
  }




}
