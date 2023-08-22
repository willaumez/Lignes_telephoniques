package com.telephone.backendlignestelephoniques.services.TypeLigne;

import com.telephone.backendlignestelephoniques.entities.Attribut;
import com.telephone.backendlignestelephoniques.entities.TypeLigne;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;

import java.util.List;

public interface TypeLigneService {


    //=============================================  TypeLigne  ========================================================//
    void saveTypeLigne(TypeLigne typeLigne, String operateur);
    TypeLigne getTypeLigne(Long typeLigneId) throws ElementNotFoundException;
    void deleteTypeLigne(Long id, String operateur) throws ElementNotFoundException;
    TypeLigne updateTypeLigne(TypeLigne typeLigne, String operateur);
    List<TypeLigne> listTypeLigne();

    void associateAttributesWithType(Long typeLigneId, List<Long> attributs) throws ElementNotFoundException;

}
