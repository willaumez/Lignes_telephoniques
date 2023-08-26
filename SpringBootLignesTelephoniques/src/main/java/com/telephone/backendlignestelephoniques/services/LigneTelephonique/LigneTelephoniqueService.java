package com.telephone.backendlignestelephoniques.services.LigneTelephonique;

import com.telephone.backendlignestelephoniques.dtos.LigneTelephoniqueDto;
import com.telephone.backendlignestelephoniques.entities.*;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;

import java.util.List;

public interface LigneTelephoniqueService {


    //=============================================  LigneTelephonique  ========================================================//
    // Enregistre une nouvelle ligne téléphonique
    void saveLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur) throws ElementNotFoundException;

    // Récupère une ligne téléphonique par son ID
    LigneTelephonique getLigneTelephonique(Long ligneId) throws ElementNotFoundException;

    // Supprime une ligne téléphonique par son ID
    void deleteLigneTelephonique(Long id, String operateur) throws ElementNotFoundException;

    // Met à jour une ligne téléphonique
    LigneTelephonique updateLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur);

    // Récupère la liste de toutes les lignes téléphoniques
    List<LigneTelephoniqueDto> listLigneTelephonique();

    // Récupère la liste de lignes téléphoniques en fonction d'un type de ligne donné
    List<LigneTelephonique> listLigneTelephoniqueByType(long typeLigneId);

}
