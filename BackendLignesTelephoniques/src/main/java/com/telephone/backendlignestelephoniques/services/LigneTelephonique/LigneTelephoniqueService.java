package com.telephone.backendlignestelephoniques.services.LigneTelephonique;

import com.telephone.backendlignestelephoniques.entities.LigneTelephonique;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;

import java.util.List;

public interface LigneTelephoniqueService {


    //=============================================  LigneTelephonique  ========================================================//
    LigneTelephonique saveLigneTelephonique(LigneTelephonique ligneTelephonique);
    LigneTelephonique getLigneTelephonique(Long ligneId) throws ElementNotFoundException;
    void deleteLigneTelephonique(Long id);
    LigneTelephonique updateLigneTelephonique(LigneTelephonique ligneTelephonique);
    List<LigneTelephonique> listLigneTelephonique();
}
