package com.telephone.backendlignestelephoniques.services.Restoration;

import com.telephone.backendlignestelephoniques.entities.Restoration;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;

import java.util.List;

public interface RestorationService {



    //=============================================  Restoration  ========================================================//
    void saveRestoration(Restoration restoration, String operateur);
    Restoration getRestoration(Long restorationId) throws ElementNotFoundException;
    void deleteRestoration(Long id, String operateur);
    void restorer(Long id, String operateur) throws ElementNotFoundException;
    List<Restoration> listRestoration();

    void deleteOldRestorations();

}
