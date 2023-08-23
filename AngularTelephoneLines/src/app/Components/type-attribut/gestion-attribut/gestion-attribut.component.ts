import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Attribut, TypeVariable} from "../../../Models/Attribut";
import {TypeLigne} from "../../../Models/TypeLigne";
import {Section2} from "../gestion-type/gestion-type.component";
import {MatChipInputEvent} from "@angular/material/chips";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {LiveAnnouncer} from "@angular/cdk/a11y";

@Component({
  selector: 'app-gestion-attribut',
  templateUrl: './gestion-attribut.component.html',
  styleUrls: ['./gestion-attribut.component.scss']
})
export class GestionAttributComponent {
  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  selectAdd: boolean = false;
  attributForm!: FormGroup;
  typeVariables: string[] = Object.values(TypeVariable);

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


  constructor(private _fb: FormBuilder, private _announcer: LiveAnnouncer) {
    this.attributForm = this._fb.group({
      idAttribut: [null],
      nomAttribut: ['', Validators.required],
      type: ['', Validators.required],
      valeurDefaut: [''],
      valeurAttribut: [''],
      enumeration: this._fb.array([]),
    });
  }

  handleAdd():void {
    this.selectAdd = !this.selectAdd;
  }

  onAttributSubmit(): void {
    if (this.attributForm.valid) {
      // Soumettre le formulaire
      const formData = this.attributForm.value;
      console.log(formData); // Utilisez les données du formulaire comme nécessaire
    }
  }


  //enum
  addEnum(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Ajouter un nouvel élément à la liste d'énumération
    if (value) {
      this.attributForm.get('enumeration')!.value.push(value);
    }

    // Effacer la valeur de l'input
    event.chipInput!.clear();
  }

  removeEnum(enumValue: string): void {
    const enumeration = this.attributForm.get('enumeration')!.value;
    const index = enumeration.indexOf(enumValue);

    if (index >= 0) {
      enumeration.splice(index, 1);

      this._announcer.announce(`Removed ${enumValue}`);
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
