import {TypeLigne} from "./TypeLigne";

export interface LigneTelephonique {
  idLigne: number;
  numeroLigne: string;
  affectation: string;
  poste: string;
  etat: EtatType;
  dateLivraison: Date;
  numeroSerie: string;
  montant: number;
  createdDate: Date;
  typeLigne: TypeLigne;
  ligneAttributs: [] | null;
}

export enum EtatType {
  ACTIF = "ACTIF",
  RESILIE = "RESILIE",
  CESSION = "CESSION"
}
