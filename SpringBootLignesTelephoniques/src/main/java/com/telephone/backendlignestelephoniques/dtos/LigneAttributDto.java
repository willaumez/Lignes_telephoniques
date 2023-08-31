package com.telephone.backendlignestelephoniques.dtos;

import com.telephone.backendlignestelephoniques.entities.Attribut;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LigneAttributDto {
    private Long id;
    private Long ligneId;  // Utilisez Long pour l'ID de LigneTelephonique
    private AttributDto attribut;  // Notez que c'est maintenant un AttributDto
    private String valeurAttribut;
}