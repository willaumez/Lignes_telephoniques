import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Attribut} from "../../../Models/Attribut";
import {TypeLigne} from "../../../Models/TypeLigne";

export interface Section2 {
  name: string;
  updated: Date;
}
@Component({
  selector: 'app-gestion-type',
  templateUrl: './gestion-type.component.html',
  styleUrls: ['./gestion-type.component.scss']
})
export class GestionTypeComponent {

  selectAdd: boolean = false;
  typeForm!: FormGroup;
  attributs: Attribut[] = [];

  typeLignes: TypeLigne[] = [
    {
      idType: 1,
      nomType: 'Type 1',
      descriptionType: 'Description du Type 1',
      createdDate: new Date('2023-08-01'),
      lignesTelephoniques: [],
      attributs: []
    },
    {
      idType: 2,
      nomType: 'Type 2',
      descriptionType: 'Description du Type 2',
      createdDate: new Date('2023-08-10'),
      lignesTelephoniques: [],
      attributs: []
    },
    // Ajoutez plus d'éléments ici avec des données aléatoires
  ];

  folders: Section2[] = [
    {
      name: 'Photos',
      updated: new Date('1/1/16'),
    },
    {
      name: 'Recipes',
      updated: new Date('1/17/16'),
    },
    {
      name: 'Work',
      updated: new Date('1/28/16'),
    },
  ];
  notes: Section2[] = [
    {
      name: 'Vacation Itinerary',
      updated: new Date('2/20/16'),
    },
    {
      name: 'Kitchen Remodel',
      updated: new Date('1/18/16'),
    },
  ];

  constructor(private _fb: FormBuilder) {
    this.typeForm = this._fb.group({
      idType: [null],
      nomType: ['', Validators.required],
      descriptionType: ['', Validators.maxLength(255)],
      createdDate: [null],
      lignesTelephoniques: [null],
      attributs: [null],
    });
  }

  handleAdd():void {
    this.selectAdd = !this.selectAdd;
  }

  onTypeSubmit(): void {
    if (this.typeForm.valid) {
      // Soumettre le formulaire
      const formData = this.typeForm.value;
      console.log(formData); // Utilisez les données du formulaire comme nécessaire
    }
  }

}
