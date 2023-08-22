import { Component } from '@angular/core';
import {TypeLigne} from "../../Models/TypeLigne";

export interface Section {
  name: string;
  updated: Date;
}
@Component({
  selector: 'app-type-attribut',
  templateUrl: './type-attribut.component.html',
  styleUrls: ['./type-attribut.component.scss']
})
export class TypeAttributComponent {
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

  folders: Section[] = [
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
  notes: Section[] = [
    {
      name: 'Vacation Itinerary',
      updated: new Date('2/20/16'),
    },
    {
      name: 'Kitchen Remodel',
      updated: new Date('1/18/16'),
    },
  ];

  constructor() {
  }


}
