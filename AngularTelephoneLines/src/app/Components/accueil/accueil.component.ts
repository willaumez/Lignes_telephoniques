import {Component, OnInit} from '@angular/core';
import {LigneTelephoniqueService} from "../../Services/ligne-telephonique.service";
import {HomeResponse, PagedResponse, tableResponse} from "../../Models/PagedResponse";
import {User} from "../../Models/User";
import {MatTableDataSource} from "@angular/material/table";

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
        this.dataSource = [];
        this.dataSource.push({nom: "Toutes les lignes", valeur: data.totalLigne, pourcentage: 100});
        for (let element of data.typeLigne){
          let pourcentage = (element.nombreLigne / data.totalLigne) * 100;
          pourcentage = parseFloat(pourcentage.toFixed(2));
          this.dataSource.push({nom: element.nomLigne, valeur: element.nombreLigne, pourcentage: pourcentage});
        }
        console.log(JSON.stringify(this.dataHome, null, 2));
        this.isDownload = false;
      },
      error: (error) => {
        this.errorMessage = ('Erreur lors de la récupération des données: ' + error);
        this.isDownload = false;
        //console.log(JSON.stringify(error, null, 2));
      }
    });
  }


}
