package com.telephone.backendlignestelephoniques.services.LigneTelephonique;

import com.telephone.backendlignestelephoniques.entities.LigneTelephonique;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;

import java.util.List;

public interface LigneTelephoniqueService {


    //=============================================  LigneTelephonique  ========================================================//
    void saveLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur);
    LigneTelephonique getLigneTelephonique(Long ligneId) throws ElementNotFoundException;
    void deleteLigneTelephonique(Long id, String operateur) throws ElementNotFoundException;
    LigneTelephonique updateLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur);
    List<LigneTelephonique> listLigneTelephonique();
}
