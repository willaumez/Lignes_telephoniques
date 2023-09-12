import {Component, OnInit} from '@angular/core';
import {LigneTelephoniqueService} from "../../Services/ligne-telephonique.service";
import {HomeResponse, tableResponse} from "../../Models/PagedResponse";

@Component({
  selector: 'app-accueil',
  templateUrl: './accueil.component.html',
  styleUrls: ['./accueil.component.scss']
})
export class AccueilComponent implements OnInit{
  dataHome!: HomeResponse;
  //pointer
  isDownload: boolean = false;
  errorMessage!: string;

  displayedColumns: string[] = ['reference', 'valeur', 'pourcentage'];
  dataSource!: tableResponse[];
  dataSource2!: tableResponse[];

  constructor(private ligneService: LigneTelephoniqueService) {
  }
  ngOnInit(): void {
    this.getDataHome();
  }

  getDataHome(){
    this.isDownload = true;
    this.ligneService.getDataHome().subscribe({
      next: (data: HomeResponse): void => {
        this.dataHome = data;
        this.dataSource2 = [];
        this.dataSource = [];
        this.dataSource.push({nom: "Toutes les lignes", valeur: data.totalLigne, pourcentage: data.totalLigne > 0 ? 100 : 0});
        for (let element of data.typeLigne){
          let pourcentage = (element.nombreLigne / data.totalLigne) * 100;
          pourcentage = parseFloat(pourcentage.toFixed(2));
          this.dataSource.push({nom: element.nomLigne, valeur: element.nombreLigne, pourcentage: pourcentage});
        }
        for (let [nom, valeur] of Object.entries(data.etats)) {
          let pourcentage = (valeur / data.totalLigne) * 100;
          pourcentage = parseFloat(pourcentage.toFixed(2));
          this.dataSource2.push({nom: nom, valeur: valeur, pourcentage: pourcentage});
        }
        this.isDownload = false;
      },
      error: (error) => {
        this.errorMessage = ('Erreur lors de la récupération des données: ' + error);
        this.isDownload = false;
      }
    });
  }


}
