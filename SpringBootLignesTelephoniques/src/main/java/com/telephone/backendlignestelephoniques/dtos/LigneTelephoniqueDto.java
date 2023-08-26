package com.telephone.backendlignestelephoniques.dtos;

import com.telephone.backendlignestelephoniques.entities.LigneAttribut;
import com.telephone.backendlignestelephoniques.entities.TypeLigne;
import com.telephone.backendlignestelephoniques.enums.EtatType;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
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
    private TypeLigneDto typeLigne;
    private Set<LigneAttributDto> ligneAttributs;
}
