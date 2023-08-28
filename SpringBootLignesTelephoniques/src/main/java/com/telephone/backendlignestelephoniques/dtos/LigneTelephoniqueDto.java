package com.telephone.backendlignestelephoniques.dtos;

import com.telephone.backendlignestelephoniques.enums.EtatType;
import java.util.Date;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LigneTelephoniqueDto {
    private Long idLigne;
    private String numeroLigne;
    private String affectation;
    private String poste;
    private EtatType etat;
    private Date dateLivraison;
    private String numeroSerie;
    private Double montant;
    private Date createdDate;
    private Long typeId;  // Utilisez Long pour l'ID du TypeLigne
    private Set<LigneAttributDto> ligneAttributs;
}