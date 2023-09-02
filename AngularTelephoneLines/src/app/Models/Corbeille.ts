
// Interface pour le modèle Corbeille
import {EtatType} from "./LigneTelephonique";

export interface Corbeille {
  idCorbeille?: number;
  dateSuppression?: Date;
  numeroLigne?: string;
  affectation?: string;
  poste?: string;
  etat?: EtatType;
  dateLivraison?: Date;
  numeroSerie?: string;
  montant?: number;
  createdDate?: Date;
  typeId?: number;
  nomType?: string;
  descriptionType?: string;
  attributValeurs?: AttributValeur[];
}

export interface AttributValeur {
  nomAttribut: string;
  valeurAttribut: string;
  enumeration?: string;
}

