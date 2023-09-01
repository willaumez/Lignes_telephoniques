package com.telephone.backendlignestelephoniques.services.Attribut;

import com.telephone.backendlignestelephoniques.entities.Attribut;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface AttributService {

    //=============================================  Attribut  ========================================================//
    // Enregistre un nouvel attribut
    void saveAttribut(Attribut attribut, String operateur);

    // Récupère un attribut par son ID
    Attribut getAttribut(Long attributId) throws ElementNotFoundException;

    // Supprime un attribut par son ID
    void deleteAttribut(Long attributId, String operateur) throws ElementNotFoundException;

    // Met à jour un attribut
    Attribut updateAttribut(Attribut attribut, String operateur) throws ElementNotFoundException;

    // Récupère la liste de tous les attributs
    List<Attribut> listAttribut();

    Set<String> listAttributNames();

    Set<String> listAttributNamesByType(long idType) throws ElementNotFoundException;

    Page<Attribut> listAttributsPage(int page, int size, String kw);

}
