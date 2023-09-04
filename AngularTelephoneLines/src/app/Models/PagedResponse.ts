export interface PagedResponse<T> {
  dataElements: T[];
  currentPage: number;
  totalItems: number;
  totalPages: number;
}


export interface RestoreResponse {
  restoredCount: number;
  notRestoredCount: number;
}

export interface HomeResponse {
  totalLigne: number;
  typeLigne: typeLigneResponse[];
  etats: [];
}

export interface typeLigneResponse {
  nomLigne: string;
  nombreLigne: number;
}



export interface tableResponse {
  nom: string;
  valeur: number;
  pourcentage: number;
}

