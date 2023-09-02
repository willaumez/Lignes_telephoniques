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

