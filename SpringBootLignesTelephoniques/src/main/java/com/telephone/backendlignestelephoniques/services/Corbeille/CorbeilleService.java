package com.telephone.backendlignestelephoniques.services.Corbeille;

import com.telephone.backendlignestelephoniques.entities.Corbeille;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;

import java.util.List;

public interface CorbeilleService {



    //=============================================  Corbeille  ========================================================//
    void saveRestoration(Corbeille corbeille, String operateur);
    Corbeille getRestoration(Long restorationId) throws ElementNotFoundException;
    void deleteRestoration(Long id, String operateur);
    void restorer(Long id, String operateur) throws ElementNotFoundException;
    List<Corbeille> listRestoration();

    void deleteOldRestorations();

}
