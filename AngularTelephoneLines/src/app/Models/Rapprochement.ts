export interface Rapprochement {
  numero: string;
  montant: number;
}
export interface RapprochementMontant {
  numero: string;
  montantDb: number;
  montantExcel: number;
}
export interface VerificationResult {
  isValid: boolean;
  message: string;
}


export interface ImportationResult {
  savedCount: number;
  notSavedCount: number;
  nullNumbersCount: number;
  failedNumbers: [];
}


