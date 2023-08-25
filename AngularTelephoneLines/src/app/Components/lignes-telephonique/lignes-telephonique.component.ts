import { Component } from '@angular/core';

@Component({
  selector: 'app-lignes-telephonique',
  templateUrl: './lignes-telephonique.component.html',
  styleUrls: ['./lignes-telephonique.component.scss']
})
export class LignesTelephoniqueComponent {
  displayedColumns: string[] = ['idLigne', 'numeroLigne', 'affectation', 'poste', 'etat', 'dateLivraison', 'numeroSerie', 'montant', 'typeLigne', 'actions'];
  dataSource = [
    // Exemple de données
    {
      idLigne: 1,
      numeroLigne: '123456',
      affectation: 'Département IT',
      poste: 'Poste XYZ',
      etat: 'Actif',
      dateLivraison: new Date('2023-08-25'),
      numeroSerie: 'SER12345',
      montant: 100.50,
      typeLigne: { nomType: 'Type A' }
    },
    {
      idLigne: 1,
      numeroLigne: '123456',
      affectation: 'Département IT',
      poste: 'Poste XYZ',
      etat: 'Actif',
      dateLivraison: new Date('2023-08-25'),
      numeroSerie: 'SER12345',
      montant: 100.50,
      typeLigne: { nomType: 'Type A' }
    },
    {
      idLigne: 1,
      numeroLigne: '123456',
      affectation: 'Département IT',
      poste: 'Poste XYZ',
      etat: 'Actif',
      dateLivraison: new Date('2023-08-25'),
      numeroSerie: 'SER12345',
      montant: 100.50,
      typeLigne: { nomType: 'Type A' }
    },{
      idLigne: 1,
      numeroLigne: '123456',
      affectation: 'Département IT',
      poste: 'Poste XYZ',
      etat: 'Actif',
      dateLivraison: new Date('2023-08-25'),
      numeroSerie: 'SER12345',
      montant: 100.50,
      typeLigne: { nomType: 'Type A' }
    },{
      idLigne: 1,
      numeroLigne: '123456',
      affectation: 'Département IT',
      poste: 'Poste XYZ',
      etat: 'Actif',
      dateLivraison: new Date('2023-08-25'),
      numeroSerie: 'SER12345',
      montant: 100.50,
      typeLigne: { nomType: 'Type A' }
    },{
      idLigne: 1,
      numeroLigne: '123456',
      affectation: 'Département IT',
      poste: 'Poste XYZ',
      etat: 'Actif',
      dateLivraison: new Date('2023-08-25'),
      numeroSerie: 'SER12345',
      montant: 100.50,
      typeLigne: { nomType: 'Type A' }
    },{
      idLigne: 1,
      numeroLigne: '123456',
      affectation: 'Département IT',
      poste: 'Poste XYZ',
      etat: 'Actif',
      dateLivraison: new Date('2023-08-25'),
      numeroSerie: 'SER12345',
      montant: 100.50,
      typeLigne: { nomType: 'Type A' }
    },
    // Ajoutez d'autres lignes ici
  ];

  ajouter() {
    // Logique pour ajouter une ligne
  }

  modifier(element: any) {
    // Logique pour modifier une ligne
  }

  supprimer(id: number) {
    // Logique pour supprimer une ligne
  }

}
