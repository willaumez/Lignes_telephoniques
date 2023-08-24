package com.telephone.backendlignestelephoniques.services.TypeLigne;

import com.telephone.backendlignestelephoniques.dtos.TypeLigneDTO;
import com.telephone.backendlignestelephoniques.entities.Attribut;
import com.telephone.backendlignestelephoniques.entities.TypeLigne;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;

import java.util.List;

public interface TypeLigneService {


    //=============================================  TypeLigne  ========================================================//
    void saveTypeLigne(TypeLigneDTO typeLigneDTO, String operateur);
    TypeLigneDTO getTypeLigne(Long typeLigneId) throws ElementNotFoundException;
    void deleteTypeLigne(Long id, String operateur) throws ElementNotFoundException;
    TypeLigneDTO updateTypeLigne(TypeLigneDTO typeLigneDTO, String operateur);
    List<TypeLigneDTO> listTypeLigne();

    void associateAttributesWithType(Long typeLigneId, List<Long> attributs) throws ElementNotFoundException;

}
