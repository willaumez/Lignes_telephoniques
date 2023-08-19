package com.telephone.backendlignestelephoniques.services.LigneTelephonique;

import com.telephone.backendlignestelephoniques.entities.*;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;

import java.util.List;

public interface LigneTelephoniqueService {


    //=============================================  LigneTelephonique  ========================================================//
    void saveLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur);
    LigneTelephonique getLigneTelephonique(Long ligneId) throws ElementNotFoundException;
    void deleteLigneTelephonique(Long id, String operateur) throws ElementNotFoundException;
    LigneTelephonique updateLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur);
    List<LigneTelephonique> listLigneTelephonique();
    List<LigneTelephonique> ligneByDebit(Debit debit);

    List<LigneTelephonique> ligneByDirection(Direction direction);

    List<LigneTelephonique> ligneByFonction(Fonction fonction);

    List<LigneTelephonique> ligneByForfait(Forfait forfait);

    List<LigneTelephonique> ligneByNature(NatureLigne natureLigne);

    List<LigneTelephonique> ligneByType(TypeLigne typeLigne);


}
