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
