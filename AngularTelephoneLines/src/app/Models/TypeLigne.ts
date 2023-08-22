import {LigneTelephonique} from "./LigneTelephonique";
import {Attribut} from "./Attribut";

export interface TypeLigne {
  idType: number;
  nomType: string;
  descriptionType: string;
  createdDate: Date;
  lignesTelephoniques: LigneTelephonique[] | null;
  attributs: Attribut[] | null;
}
