package com.telephone.backendlignestelephoniques.services.Fonction;

import com.telephone.backendlignestelephoniques.entities.Fonction;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;

import java.util.List;

public interface FonctionService {


    //=============================================  Fonction  ========================================================//
    void saveFonction(Fonction fonction, String operateur);
    Fonction getFonction(Long fonctionId) throws ElementNotFoundException;
    void deleteFonction(Long id, String operateur) throws ElementNotFoundException;
    Fonction updateFonction(Fonction fonction, String operateur);
    List<Fonction> listFonctions();

}
