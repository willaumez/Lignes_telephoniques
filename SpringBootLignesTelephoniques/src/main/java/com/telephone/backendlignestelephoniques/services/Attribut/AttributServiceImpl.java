package com.telephone.backendlignestelephoniques.services.Attribut;

import com.telephone.backendlignestelephoniques.entities.Attribut;
import com.telephone.backendlignestelephoniques.entities.TypeLigne;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.mappers.LigneMappers;
import com.telephone.backendlignestelephoniques.repositories.AttributRepository;
import com.telephone.backendlignestelephoniques.repositories.LigneAttributRepository;
import com.telephone.backendlignestelephoniques.repositories.TypeLigneRepository;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
/*
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AttributServiceImpl implements AttributService {

    private AttributRepository attributRepository;
    private HistoriqueService historiqueService;
    private TypeLigneRepository typeLigneRepository;
    private LigneMappers ligneMappers;
    private LigneAttributRepository ligneAttributRepository;

    @Override
    @Transactional
    public void saveAttribut(Attribut attribut, String operateur) {
        // Vérifier si l'objet attribut ou le champ nomAttribut est null
        if (attribut == null || attribut.getNomAttribut() == null) {
            throw new IllegalArgumentException("L'attribut ou le nom de l'attribut ne peut pas être null.");
        }
        // Vérifiez si un attribut du même nom existe déjà
        if (attributRepository.existsByNomAttribut(attribut.getNomAttribut())) {
            throw new IllegalArgumentException("Un attribut du même nom existe déjà.");
        }
        // Enregistrez le nouvel attribut
        attributRepository.save(attribut);
        // Enregistrez l'action dans l'historique
        if (historiqueService != null) {
            historiqueService.saveHistoriques("Ajout [Attribut]", attribut.getNomAttribut(), operateur);
        }
    }

    @Override
    public Attribut getAttribut(Long attributId) throws ElementNotFoundException {
        return attributRepository.findById(attributId)
                .orElseThrow(() -> new ElementNotFoundException("Attribut not found"));
    }

    @Override
    public void deleteAttribut(Long attributId, String operateur) throws ElementNotFoundException {
        // Trouver l'attribut par son ID
        Attribut attribut = getAttribut(attributId);

        // Supprimer toutes les références dans LigneAttribut
        if (ligneAttributRepository != null) {
            ligneAttributRepository.deleteLigneAttributByAttributIdAttribut(attributId);
        }
        // Supprimer les énumérations associées à l'attribut
        attribut.setEnumeration(null);
        // Trouver et mettre à jour les types de lignes associés à l'attribut
        List<TypeLigne> typeLignes = typeLigneRepository.findByAttributId(attributId);
        if (typeLignes != null) {
            for (TypeLigne typeLigne : typeLignes) {
                if (typeLigne.getAttributs() != null) {
                    typeLigne.getAttributs().remove(attribut);
                    typeLigneRepository.save(typeLigne);
                }
            }
        }
        // Supprimer l'attribut
        attributRepository.deleteById(attributId);
        // Enregistrer l'action dans un historique
        if (historiqueService != null) {
            historiqueService.saveHistoriques("Suppression [Attribut]", attribut.getNomAttribut(), operateur);
        }
    }


    @Override
    public Attribut updateAttribut(Attribut attribut, String operateur) throws ElementNotFoundException {
        // Trouver l'attribut existant par son ID
        Attribut existingAttribut = getAttribut(attribut.getIdAttribut());

        // Vérifier si le nom d'attribut est unique
        Attribut attributWithSameName = attributRepository.findByNomAttribut(attribut.getNomAttribut());
        if (attributWithSameName != null && !attributWithSameName.getIdAttribut().equals(attribut.getIdAttribut())) {
            throw new IllegalArgumentException("Un attribut du même nom existe déjà.");
        }

        // Mettre à jour les propriétés de l'attribut
        existingAttribut.setNomAttribut(attribut.getNomAttribut());
        existingAttribut.setType(attribut.getType());
        existingAttribut.setValeurAttribut(attribut.getValeurAttribut());
        existingAttribut.setEnumeration(attribut.getEnumeration());

        // Trouver et mettre à jour les types de lignes associés à l'attribut
        List<TypeLigne> typeLignes = typeLigneRepository.findByAttributId(existingAttribut.getIdAttribut());
        if (typeLignes != null) {
            for (TypeLigne typeLigne : typeLignes) {
                if (typeLigne.getAttributs() != null) {
                    typeLigne.getAttributs().remove(existingAttribut);
                    typeLigne.getAttributs().add(existingAttribut);
                    typeLigneRepository.save(typeLigne);
                }
            }
        }

        // Sauvegarder l'attribut mis à jour
        Attribut updatedAttribut = attributRepository.save(existingAttribut);

        // Enregistrer l'action dans un historique
        if (historiqueService != null) {
            historiqueService.saveHistoriques("Mise à jour [Attribut]", updatedAttribut.getNomAttribut(), operateur);
        }

        return updatedAttribut;
    }


    @Override
    public List<Attribut> listAttribut() {
        return attributRepository.findAll();
    }

    @Override
    public Set<String> listAttributNames() {
        List<Attribut> attributs = attributRepository.findAll();  // Récupérer tous les attributs de la base de données
        return ligneMappers.fromAttributsToNames(new HashSet<>(attributs));  // Utiliser le mapper pour obtenir les noms
    }
}*/

@Service
@Transactional  // Utilisation de @Transactional au niveau de la classe
@AllArgsConstructor
@Slf4j
public class AttributServiceImpl implements AttributService {

    private final AttributRepository attributRepository;
    private final HistoriqueService historiqueService;
    private final TypeLigneRepository typeLigneRepository;
    private final LigneMappers ligneMappers;
    private final LigneAttributRepository ligneAttributRepository;

    // Suppression de @Transactional au niveau de la méthode
    @Override
    public void saveAttribut(Attribut attribut, String operateur) {
        validateAttribut(attribut);  // Validation de l'attribut
        attributRepository.save(attribut);
        logAction("Ajout [Attribut]", attribut.getNomAttribut(), operateur);
    }

    @Override
    public Attribut getAttribut(Long attributId) throws ElementNotFoundException {
        return attributRepository.findById(attributId)
                .orElseThrow(() -> new ElementNotFoundException("Attribut not found"));
    }

    @Override
    public void deleteAttribut(Long attributId, String operateur) throws ElementNotFoundException {
        Attribut attribut = getAttribut(attributId);
        removeReferences(attributId);  // Suppression des références
        attributRepository.deleteById(attributId);
        logAction("Suppression [Attribut]", attribut.getNomAttribut(), operateur);
    }

    @Override
    public Attribut updateAttribut(Attribut attribut, String operateur) throws ElementNotFoundException {
        Attribut existingAttribut = getAttribut(attribut.getIdAttribut());
        validateUpdate(existingAttribut, attribut);  // Validation de la mise à jour
        updateProperties(existingAttribut, attribut);  // Mise à jour des propriétés
        logAction("Mise à jour [Attribut]", existingAttribut.getNomAttribut(), operateur);
        return attributRepository.save(existingAttribut);
    }

    @Override
    public List<Attribut> listAttribut() {
        return attributRepository.findAll();
    }

    @Override
    public Set<String> listAttributNames() {
        List<Attribut> attributs = attributRepository.findAll();
        return ligneMappers.fromAttributsToNames(new HashSet<>(attributs));
    }

    private void validateAttribut(Attribut attribut) {
        if (attribut == null || attribut.getNomAttribut() == null) {
            throw new IllegalArgumentException("L'attribut ou le nom de l'attribut ne peut pas être null.");
        }
        if (attributRepository.existsByNomAttribut(attribut.getNomAttribut())) {
            throw new IllegalArgumentException("Un attribut du même nom existe déjà.");
        }
    }

    private void validateUpdate(Attribut existingAttribut, Attribut newAttribut) {
        Attribut attributWithSameName = attributRepository.findByNomAttribut(newAttribut.getNomAttribut());
        if (attributWithSameName != null && !attributWithSameName.getIdAttribut().equals(existingAttribut.getIdAttribut())) {
            throw new IllegalArgumentException("Un attribut du même nom existe déjà.");
        }
    }

    private void updateProperties(Attribut existingAttribut, Attribut newAttribut) {
        existingAttribut.setNomAttribut(newAttribut.getNomAttribut());
        existingAttribut.setType(newAttribut.getType());
        existingAttribut.setValeurAttribut(newAttribut.getValeurAttribut());
        existingAttribut.setEnumeration(newAttribut.getEnumeration());
    }

    private void removeReferences(Long attributId) {
        ligneAttributRepository.deleteLigneAttributByAttributIdAttribut(attributId);
        List<TypeLigne> typeLignes = typeLigneRepository.findByAttributId(attributId);
        if (typeLignes != null) {
            for (TypeLigne typeLigne : typeLignes) {
                typeLigne.getAttributs().removeIf(attribut -> attribut.getIdAttribut().equals(attributId));
                typeLigneRepository.save(typeLigne);
            }
        }
    }

    private void logAction(String action, String element, String operateur) {
        if (historiqueService != null) {
            historiqueService.saveHistoriques(action, element, operateur);
        }
    }
}
