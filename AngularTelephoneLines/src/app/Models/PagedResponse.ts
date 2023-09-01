export interface PagedResponse<T> {
  dataElements: T[];
  currentPage: number;
  totalItems: number;
  totalPages: number;
}
