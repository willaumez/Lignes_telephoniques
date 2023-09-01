package com.telephone.backendlignestelephoniques.services.TypeLigne;

import com.telephone.backendlignestelephoniques.entities.TypeLigne;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TypeLigneService {


    //=============================================  TypeLigne  ========================================================//
    void saveTypeLigne(TypeLigne typeLigne, String operateur);
    TypeLigne getTypeLigne(Long typeLigneId) throws ElementNotFoundException;

    String getTypeLigneNomType(Long typeLigneId) throws ElementNotFoundException;

    void deleteTypeLigne(Long id, String operateur) throws ElementNotFoundException;
    void updateTypeLigne(TypeLigne typeLigne, String operateur);
    List<TypeLigne> listTypeLigne();

    Page<TypeLigne> listTypeLignePage(int page, int size, String kw);

    //void associateAttributesWithType2(Long typeLigneId, List<Long> attributs) throws ElementNotFoundException;

}
