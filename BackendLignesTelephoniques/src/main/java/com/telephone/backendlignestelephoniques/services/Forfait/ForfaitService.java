package com.telephone.backendlignestelephoniques.services.Forfait;

import com.telephone.backendlignestelephoniques.entities.Forfait;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;

import java.util.List;

public interface ForfaitService {


    //=============================================  Forfait  ========================================================//
    void saveForfait(Forfait forfait, String operateur);
    Forfait getForfait(Long forfaitId) throws ElementNotFoundException;
    void deleteForfait(Long id, String operateur) throws ElementNotFoundException;
    Forfait updateForfait(Forfait forfait, String operateur);
    List<Forfait> listForfaits();

}
