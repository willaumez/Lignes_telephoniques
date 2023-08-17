package com.telephone.backendlignestelephoniques.services.TypeLigne;

import com.telephone.backendlignestelephoniques.entities.TypeLigne;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;

import java.util.List;

public interface TypeLigneService {


    //=============================================  TypeLigne  ========================================================//
    void saveTypeLigne(TypeLigne typeLigne);
    TypeLigne getTypeLigne(Long typeLigneId) throws ElementNotFoundException;
    void deleteTypeLigne(Long id) throws ElementNotFoundException;
    TypeLigne updateTypeLigne(TypeLigne typeLigne);
    List<TypeLigne> listTypeLigne();


}
