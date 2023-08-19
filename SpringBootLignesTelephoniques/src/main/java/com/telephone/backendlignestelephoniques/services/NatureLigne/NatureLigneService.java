package com.telephone.backendlignestelephoniques.services.NatureLigne;

import com.telephone.backendlignestelephoniques.entities.NatureLigne;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;

import java.util.List;

public interface NatureLigneService {


    //=============================================  NatureLigne  ========================================================//
    void saveNatureLigne(NatureLigne natureLigne, String operateur);
    NatureLigne getNatureLigne(Long natureId) throws ElementNotFoundException;
    void deleteNatureLigne(Long id, String operateur) throws ElementNotFoundException;
    NatureLigne updateNatureLigne(NatureLigne natureLigne, String operateur);
    List<NatureLigne> listNatureLigne();

}
