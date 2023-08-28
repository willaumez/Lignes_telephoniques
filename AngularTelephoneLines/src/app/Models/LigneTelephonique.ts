import {TypeLigne} from "./TypeLigne";
import {LigneAttribut} from "./LigneAttributs";

export interface LigneTelephonique {
  idLigne?: number;
  typeId?: number;
  numeroLigne: string;
  affectation?: string;
  poste?: string;
  etat?: EtatType;
  dateLivraison?: Date;
  numeroSerie?: string;
  montant?: number;
  createdDate: Date;
  typeLigne: TypeLigne;
  ligneAttributs?: any[];
}

export enum EtatType {
  ACTIF = "ACTIF",
  RESILIE = "RESILIE",
  CESSION = "CESSION"
}
