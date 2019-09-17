export interface Page<T> {
  content: T[];
  first: boolean;
  last: boolean;
  number: number;
  pageable: Pageable;
  size: number;
  sort: Sort;
  totalElements: number;
  totalPages: number;
}
export interface Pageable {
  offset: number;
  pageNumber: number;
  pageSize: number;
  paged: boolean;
  unpaged: boolean;
  sort: Sort;
}

export interface Sort {
  sorded: boolean;
  unsorded: boolean;
}
