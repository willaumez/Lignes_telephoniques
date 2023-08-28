package com.telephone.backendlignestelephoniques.dtos;

import java.util.Date;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TypeLigneDto {
    private Long idType;
    private String nomType;
    private String descriptionType;
    private Date createdDate;
    private Set<Long> ligneTelephoniqueIds; // Ajouté pour référencer les LigneTelephonique associées par ID
    private Set<AttributDto> attributs;
}