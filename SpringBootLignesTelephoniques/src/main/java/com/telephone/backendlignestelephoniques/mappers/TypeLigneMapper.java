package com.telephone.backendlignestelephoniques.mappers;

import com.telephone.backendlignestelephoniques.dtos.TypeLigneDTO;
import com.telephone.backendlignestelephoniques.entities.TypeLigne;

public interface TypeLigneMapper {
    TypeLigne fromTypeLigneDTO(TypeLigneDTO typeLigneDTO);
    TypeLigneDTO fromTypeLigne(TypeLigne typeLigne);
}
