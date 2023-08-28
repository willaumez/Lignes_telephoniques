package com.telephone.backendlignestelephoniques.dtos;

import com.telephone.backendlignestelephoniques.entities.Attribut;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LigneAttributDto {
    private Long id;
    private Long ligneId;  // Utilisez Long pour l'ID de LigneTelephonique
    private AttributDto attribut;  // Notez que c'est maintenant un AttributDto
    private String valeurAttribut;
}