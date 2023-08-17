package com.telephone.backendlignestelephoniques.services.Forfait;

import com.telephone.backendlignestelephoniques.entities.Forfait;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;

import java.util.List;

public interface ForfaitService {


    //=============================================  Forfait  ========================================================//
    void saveForfait(Forfait forfait);
    Forfait getForfait(Long forfaitId) throws ElementNotFoundException;
    void deleteForfait(Long id);
    Forfait updateForfait(Forfait forfait);
    List<Forfait> listForfaits();

}
