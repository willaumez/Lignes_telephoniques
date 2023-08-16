package com.telephone.backendlignestelephoniques.services.Fonction;

import com.telephone.backendlignestelephoniques.entities.Fonction;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;

import java.util.List;

public interface FonctionService {


    //=============================================  Fonction  ========================================================//
    Fonction saveFonction(Fonction fonction);
    Fonction getFonction(Long fonctionId) throws ElementNotFoundException;
    void deleteFonction(Long id);
    Fonction updateFonction(Fonction fonction);
    List<Fonction> listFonctions();

}
