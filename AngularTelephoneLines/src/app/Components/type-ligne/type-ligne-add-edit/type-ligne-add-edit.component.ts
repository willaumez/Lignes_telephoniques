import {Component, Inject, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TypeAttributService} from "../../../Services/type-attribut.service";
import {CoreService} from "../../../core/core.service";
import {LigneTelephoniqueService} from "../../../Services/ligne-telephonique.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {LigneAttribut} from "../../../Models/LigneAttributs";

@Component({
  selector: 'app-type-ligne-add-edit',
  templateUrl: './type-ligne-add-edit.component.html',
  styleUrls: ['./type-ligne-add-edit.component.scss']
})
export class TypeLigneAddEditComponent implements OnInit {
  ligneForm: FormGroup;

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
      console.log("Add--  --  "+JSON.stringify(this.data?.typeLigne));
    }
    if (this.data?.edit) {
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
      console.log("Edit--  --  "+JSON.stringify(this.data?.ligne));
    }
  }



}
