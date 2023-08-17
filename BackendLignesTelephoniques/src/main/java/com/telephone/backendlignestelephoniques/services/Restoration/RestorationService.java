package com.telephone.backendlignestelephoniques.services.Restoration;

import com.telephone.backendlignestelephoniques.entities.Restoration;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;

import java.util.List;

public interface RestorationService {



    //=============================================  Restoration  ========================================================//
    void saveRestoration(Restoration restoration);
    Restoration getRestoration(Long restorationId) throws ElementNotFoundException;
    void deleteRestoration(Long id);
    void restoration(Long id) throws ElementNotFoundException;
    List<Restoration> listRestoration();

    void deleteOldRestorations();

}
