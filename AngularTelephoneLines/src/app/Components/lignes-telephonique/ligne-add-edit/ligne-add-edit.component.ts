import {Component, Inject, OnInit} from '@angular/core';
import {TypeAttributService} from "../../../Services/type-attribut.service";
import {CoreService} from "../../../core/core.service";
import {Attribut} from "../../../Models/Attribut";
import {TypeLigne} from "../../../Models/TypeLigne";
import {AbstractControl, FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatSelectChange} from "@angular/material/select";
import {EtatType} from "../../../Models/LigneTelephonique";
import {LigneTelephoniqueService} from "../../../Services/ligne-telephonique.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {LigneAttribut} from "../../../Models/LigneAttributs";

@Component({
  selector: 'app-ligne-add-edit',
  templateUrl: './ligne-add-edit.component.html',
  styleUrls: ['./ligne-add-edit.component.scss']
})
export class LigneAddEditComponent implements OnInit {
  errorMessage!: string;
  ligneForm: FormGroup;
  etatTypes = Object.values(EtatType);
  typesLigne: TypeLigne[] = [];

  constructor(private typeAttributService: TypeAttributService, private _coreService: CoreService,
              private ligneService: LigneTelephoniqueService, private fb: FormBuilder, private _dialogRef: MatDialogRef<LigneAddEditComponent>,
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
    this.getTypesLigne();
    if (this.data && this.data.ligneAttributs) {
      this.ligneForm.patchValue(this.data);
      this.ligneForm.get('typeLigne.idType')?.disable();
      // Récupérer le FormArray 'ligneAttributs' du formulaire principal
      const ligneAttributsArray = this.ligneForm.get('ligneAttributs') as FormArray;

      // Parcourir chaque 'LigneAttribut' dans les données
      this.data.ligneAttributs.forEach((ligneAttr: LigneAttribut) => {
        // Créer un groupe de formulaires pour cette 'LigneAttribut'
        const ligneAttrGroup = this.fb.group({
          id: [ligneAttr.id, Validators.required], // ID de la ligne attribut
          attribut: this.fb.group({  // Groupe pour les attributs
            idAttribut: [ligneAttr.attribut.idAttribut, Validators.required],
            nomAttribut: [ligneAttr.attribut.nomAttribut],
            type: [ligneAttr.attribut.type],
            enumeration: [ligneAttr.attribut.enumeration]
          }),
          valeurAttribut: [ligneAttr.valeurAttribut] // Valeur de l'attribut pour cette ligne
        });
        // Ajouter ce groupe au FormArray 'ligneAttributs'
        ligneAttributsArray.push(ligneAttrGroup);
      });
    }
  }


  //get Type Ligne
  getTypesLigne(): void {
    this.typeAttributService.getAllTypesLigne().subscribe({
        next: (data: any[]): void => {
          this.typesLigne = data;
        },
        error: (error) => {
          this._coreService.openSnackBar('Erreur lors de la récupération des types de ligne:' + error.error.message);
          this.errorMessage = ('Erreur lors de la récupération des types de ligne: ' + error.error.message)
          console.error(error.error.message);
        }
      }
    );
  }

  get attributs() {
    return (this.ligneForm.get('typeLigne.attributs') as FormArray);
  }// Ajout de la méthode get pour accéder à ligneAttributs
  get ligneAttributs(): FormArray {
    return this.ligneForm.get('ligneAttributs') as FormArray;
  }

  onTypeChange(event: MatSelectChange) {
    const typeId = event.value;
    const selectedType = this.typesLigne.find(t => t.idType === typeId);
    const attributsArray = this.ligneForm.get('typeLigne.attributs') as FormArray;
    if (selectedType && selectedType.attributs) {
      attributsArray.clear();
      selectedType.attributs.forEach((attr: Attribut) => {
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
    if (this.ligneForm.valid) {
      const formData = this.ligneForm.value;
      formData.numeroLigne = this.normalizeNumero(formData.numeroLigne);
      if (this.data) {
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

  startDate() {
    //return (new Date(2023, 0, 1));
    return new Date(Date.now());
  }


}
