export interface Item {
  id: number;
  name: string;
  date: string;
  usd: number;
  pln: number;
}

export type Items = Item[];

export interface ItemDTO {
  name: string;
  date: string;
  usd: number;
}

export interface Rate {
  no: string;
  effective_date: Date;
  bid: number;
  ask: number;
}

export interface Page<T> {
  content: T[]
  pageable: {
    page_number: number,
    page_size: number,
    sort: {
      sorted: boolean,
      unsorted: boolean,
      empty: boolean
    },
    offset: number,
    paged: boolean,
    unpaged: boolean
  }
  last: boolean
  total_pages: number
  total_elements: number
  size: number
  number: number
  sort: {
    sorted: boolean,
    unsorted: boolean,
    empty: boolean
  }
  first: boolean
  number_of_elements: number
  empty: boolean
}
