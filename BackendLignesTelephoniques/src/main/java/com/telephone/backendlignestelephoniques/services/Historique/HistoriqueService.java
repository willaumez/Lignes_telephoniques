package com.telephone.backendlignestelephoniques.services.Historique;

import com.telephone.backendlignestelephoniques.entities.Historiques;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;

import java.util.List;

public interface HistoriqueService {


    //=============================================  Historique  ========================================================//
    Historiques saveHistoriques(Historiques historiques);
    Historiques getHistoriques(Long historiqueId) throws ElementNotFoundException;
    void deleteHistoriques(Long id);
    Historiques updateHistoriques(Historiques historiques);
    List<Historiques> listHistoriques();
}
