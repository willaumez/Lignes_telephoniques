package com.telephone.backendlignestelephoniques.services.Historique;

import com.telephone.backendlignestelephoniques.entities.Historiques;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HistoriqueService {


    //=============================================  Historique  ========================================================//
    void saveHistoriques(String action, String elementCible);
    Historiques getHistoriques(Long historiqueId) throws ElementNotFoundException;
    void deleteHistoriques(Long id);
    List<Historiques> listHistoriques();

    void deleteOldHistoriques();

}
