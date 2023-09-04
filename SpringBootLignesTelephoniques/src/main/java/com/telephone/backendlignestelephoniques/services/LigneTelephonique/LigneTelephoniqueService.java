package com.telephone.backendlignestelephoniques.services.LigneTelephonique;

import com.telephone.backendlignestelephoniques.entities.*;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface LigneTelephoniqueService {


    //=============================================  LigneTelephonique  ========================================================//
    // Enregistre une nouvelle ligne téléphonique
    void saveLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur) throws ElementNotFoundException;

    // Récupère une ligne téléphonique par son ID
    LigneTelephonique getLigneTelephonique(Long ligneId) throws ElementNotFoundException;

    // Supprime une ligne téléphonique par son ID
    void deleteLigneTelephonique(Long id, String operateur) throws ElementNotFoundException;

    // Met à jour une ligne téléphonique
    void updateLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur) throws ElementNotFoundException;

    // Récupère la liste de toutes les lignes téléphoniques
    //List<LigneTelephonique> listLigneTelephonique();
    Page<LigneTelephonique> listLigneTelephonique(int page, int size, String kw);

    Page<LigneTelephonique> listLigneTelephoniqueByType(int page, int size, String kw, Long typeId);


    // Récupère la liste de lignes téléphoniques en fonction d'un type de ligne donné
    List<LigneTelephonique> listLigneTelephoniqueByType(long typeLigneId);


    //Rapprochement
    List<Rapprochement> rapprochementList();

    //Corbeille
    void saveInCorbeille(LigneTelephonique ligneTelephonique);

    Corbeille getElementCorbeille(Long restorationId) throws ElementNotFoundException;

    void deleteFromCorbeille(Long id, String operateur) throws ElementNotFoundException;

    void restorationOfElement(Long id, String operateur) throws ElementNotFoundException;

    List<Corbeille> listCorbeille();

    Page<Corbeille> listCorbeillePage(int page, int size, String kw);

    void deleteAllCorbeille(String operateur);

    Map<String, Integer> restorationAllElement(String operateur);


    //Import
    Map<String, Object> importLigneTelephonique(LigneTelephonique[] telephoniques, String operateur);

    Map<String, Object> importAccueil();


}
