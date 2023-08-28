import {ChangeDetectorRef, Component, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Rapprochement} from "../../Models/Rapprochement";
import {MatSort} from "@angular/material/sort";
import {LigneTelephoniqueService} from "../../Services/ligne-telephonique.service";
import {RapprochementService} from "../../Services/rapprochement.service";

@Component({
  selector: 'app-rapprochement',
  templateUrl: './rapprochement.component.html',
  styleUrls: ['./rapprochement.component.scss']
})
export class RapprochementComponent {
  dataFromExcel: Rapprochement[] = [];
  dataFromDataBase: Rapprochement[] = [];

  dataCorresponding: Rapprochement[] = [];
  dataMontantNoCor: Rapprochement[] = [];
  dataNoExistDB: Rapprochement[] = [];
  dataNoExistExcel: Rapprochement[] = [];


  isLoading: boolean = false;
  isFileDragging: boolean = false;
  selectedFile!: File | null;
  traitement:boolean = false;


  //Table
  displayedColumns: string[] = ['numero', 'montant'];
  dataSource!: MatTableDataSource<any>;
  dataBase!: MatTableDataSource<any>;
  tableCorresponding!: MatTableDataSource<any>;
  tableMontantNoCor!: MatTableDataSource<any>;
  tableNoExistDB!: MatTableDataSource<any>;
  tableNoExistExcel!: MatTableDataSource<any>;
  //@ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;


  ngOnInit(): void {
    this.dataSource = new MatTableDataSource(this.dataFromExcel);
    //this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.getListLignes();
  }

  constructor(private rapService: RapprochementService, private cdRef: ChangeDetectorRef,private ligneService: LigneTelephoniqueService) {
    // Create 100 users
    //const users = Array.from({length: 100}, (_, k) => createNewUser(k + 1));

    // Assign the data to the data source for the table to render
    //this.dataSource = new MatTableDataSource(users);
  }

  preventDefault(event: Event): void {
    event.preventDefault();
  }
  onFileDragOver(event: DragEvent): void {
    this.isFileDragging = true;
    event.preventDefault();
  }
  onFileDragLeave(event: DragEvent): void {
    this.isFileDragging = false;
    event.preventDefault();
  }
  onFileDrop(event: DragEvent): void {
    this.isFileDragging = false;
    event.preventDefault();

    const files = event.dataTransfer?.files;
    if (files && files.length > 0) {
      this.selectedFile = files[0];
      //this.dataFromExcel = this.rapService.importDataFromExcel(this.selectedFile);
    }
  }
  getFile(event: any): void {
    this.selectedFile = event.target.files[0];

    if (this.selectedFile) {
      //this.dataFromExcel = this.rapService.importDataFromExcel(this.selectedFile);
    } else {
      console.error("No file selected.");
    }
  }

  getListLignes() {
    this.ligneService.getLignesRapprochement().subscribe({
      next: (data) => {
        this.dataFromDataBase = data;
        this.dataBase = new MatTableDataSource(data);
        //this.dataSource.sort = this.sort;
        //this.dataSource.paginator = this.paginator;
      },
      error: err => {
        console.log(err);
      }
    });
  }

  onFormSubmit(): void {
    if (this.selectedFile) {
      this.rapService.importDataFromExcel(this.selectedFile).subscribe(
        (data) => {
          this.dataFromExcel = data;
          this.dataSource = new MatTableDataSource(data);
          this.dataSource.sort = this.sort;
          //this.dataSource.paginator = this.paginator;

          // Trigger manual change detection
          this.cdRef.detectChanges();
        },
        (error) => {
          console.error('Erreur lors de l\'importation du fichier :', error);
        }
      );
    }
  }


  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  resetPage() {
    this.dataFromExcel = [];
    this.isLoading = false;
    this.isFileDragging = false;
    this.selectedFile = null;

    this.dataCorresponding = [];
    this.dataMontantNoCor = [];
    this.dataNoExistDB = [];
    this.dataNoExistExcel = [];

    this.dataSource = new MatTableDataSource<any>();
    this.dataBase = new MatTableDataSource<any>();
    this.tableCorresponding = new MatTableDataSource<any>();
    this.tableMontantNoCor = new MatTableDataSource<any>();
    this.tableNoExistDB = new MatTableDataSource<any>();
    this.tableNoExistExcel = new MatTableDataSource<any>();

    this.traitement  = false;
  }


  /*traitementBtn() {
    //comparaison des numeros
    for (const excelItem of this.dataFromExcel) {
      for (const dbItem of this.dataFromDataBase) {
        if (excelItem.numero === dbItem.numero) {
          this.dataCorresponding.push(excelItem);
          break; // Passer au prochain élément de dataFromExcel dès qu'une correspondance est trouvée
        }
      }
    }
    this.tableCorresponding = new MatTableDataSource(this.dataCorresponding);
    console.log('this.dataCorresponding :', this.dataCorresponding);
    //comparaison des numeros montant
    for (const correspondingItem of this.dataCorresponding) {
      const excelItem = this.dataFromExcel.find(excelItem => excelItem.numero === correspondingItem.numero);

      if (excelItem && excelItem.montant !== correspondingItem.montant) {
        this.dataMontantNoCor.push(correspondingItem);
      }
    }
    this.tableMontantNoCor = new MatTableDataSource(this.dataMontantNoCor);
    console.log('this.dataMontantNoCor :', this.dataMontantNoCor);

  }*/

  traitementBtn() {

    // Comparaison des numeros
    for (const excelItem of this.dataFromExcel) {
      let numeroExisteDansDB = false;

      for (const dbItem of this.dataFromDataBase) {
        if (excelItem.numero === dbItem.numero) {
          this.dataCorresponding.push(excelItem);
          numeroExisteDansDB = true;
          break; // Passer au prochain élément de dataFromExcel dès qu'une correspondance est trouvée
        }
      }

      if (!numeroExisteDansDB) {
        this.dataNoExistDB.push(excelItem);
      }
    }

    // Comparaison des numeros montant
    for (const correspondingItem of this.dataCorresponding) {
      const excelItem = this.dataFromExcel.find(excelItem => excelItem.numero === correspondingItem.numero);

      if (excelItem && excelItem.montant !== correspondingItem.montant) {
        this.dataMontantNoCor.push(correspondingItem);
      }
    }

    // Comparaison des données inexistantes dans l'autre sens
    for (const dbItem of this.dataFromDataBase) {
      let numeroExisteDansExcel = false;

      for (const excelItem of this.dataFromExcel) {
        if (dbItem.numero === excelItem.numero) {
          numeroExisteDansExcel = true;
          break;
        }
      }

      if (!numeroExisteDansExcel) {
        this.dataNoExistExcel.push(dbItem);
      }
    }

    // Mettre à jour les sources de données pour les tables
    this.tableCorresponding = new MatTableDataSource(this.dataCorresponding);
    this.tableMontantNoCor = new MatTableDataSource(this.dataMontantNoCor);
    this.tableNoExistDB = new MatTableDataSource(this.dataNoExistDB);
    this.tableNoExistExcel = new MatTableDataSource(this.dataNoExistExcel);

    //
    this.traitement  = true;
  }







}
