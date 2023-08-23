export interface Attribut {
  idAttribut: number;
  nomAttribut: string;
  type: TypeVariable;
  valeurAttribut: string;
  enumeration: string[] | null;
}

export enum TypeVariable {
  String = 'string',
  Number = 'number',
  Boolean = 'boolean',
  Date = 'date',
  Array = 'array',
  Undefined = 'undefined',
  Symbol = 'symbol',
  BigInt = 'bigint',
  null = 'null',
}
