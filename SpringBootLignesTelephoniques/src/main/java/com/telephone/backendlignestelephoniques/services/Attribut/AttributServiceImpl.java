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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public Page<Attribut> listAttributsPage(int page, int size, String kw) {
        Pageable pageable = PageRequest.of(page, size);
        return attributRepository.getAllAttributs(kw, pageable);
    }

    @Override
    public Set<String> listAttributNames() {
        List<Attribut> attributs = attributRepository.findAll();
        return ligneMappers.fromAttributsToNames(new HashSet<>(attributs));
    }
    @Override
    public Set<String> listAttributNamesByType(long idType) throws ElementNotFoundException {
        TypeLigne typeLigne = typeLigneRepository.findByIdType(idType)
                .orElseThrow(() -> new ElementNotFoundException("Type Ligne introuvable"));
        Set<Attribut> attributs = typeLigne.getAttributs();
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
        Attribut attributWithSameName = attributRepository.findByNomAttribut(newAttribut.getNomAttribut()).orElse(null);
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
