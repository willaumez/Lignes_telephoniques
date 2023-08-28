export interface Attribut {
  idAttribut?: number;
  nomAttribut: string;
  type: TypeVariable;
  valeurAttribut?: string;
  enumeration: string[];
}

export enum TypeVariable {
  String = 'string',
  Enum = 'Enum',
  Number = 'number',
  Boolean = 'boolean',
  Date = 'date',
  Array = 'array',
  Undefined = 'undefined',
  Symbol = 'symbol',
  BigInt = 'bigint',
  null = 'null',
}
