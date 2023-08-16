package com.telephone.backendlignestelephoniques.services.Restoration;

import com.telephone.backendlignestelephoniques.entities.Restoration;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;

import java.util.List;

public interface RestorationService {



    //=============================================  Restoration  ========================================================//
    Restoration saveRestoration(Restoration restoration);
    Restoration getRestoration(Long restorationId) throws ElementNotFoundException;
    void deleteRestoration(Long id);
    Restoration updateRestoration(Restoration restoration);
    List<Restoration> listRestoration();


}
