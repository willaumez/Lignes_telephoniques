import {Attribut} from "./Attribut";

export interface TypeLigne {
  idType: number;
  nomType: string;
  descriptionType: string;
  createdDate: Date;
  attributs: Attribut[] | null;
}
