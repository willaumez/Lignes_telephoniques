package com.telephone.backendlignestelephoniques.services.NatureLigne;

import com.telephone.backendlignestelephoniques.entities.NatureLigne;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;

import java.util.List;

public interface NatureLigneService {


    //=============================================  NatureLigne  ========================================================//
    void saveNatureLigne(NatureLigne natureLigne);
    NatureLigne getNatureLigne(Long historiqueId) throws ElementNotFoundException;
    void deleteNatureLigne(Long id);
    NatureLigne updateNatureLigne(NatureLigne natureLigne);
    List<NatureLigne> listNatureLigne();

}
