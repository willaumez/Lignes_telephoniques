package com.telephone.backendlignestelephoniques.services.TypeLigne;

import com.telephone.backendlignestelephoniques.entities.Attribut;
import com.telephone.backendlignestelephoniques.entities.LigneAttribut;
import com.telephone.backendlignestelephoniques.entities.LigneTelephonique;
import com.telephone.backendlignestelephoniques.entities.TypeLigne;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.AttributRepository;
import com.telephone.backendlignestelephoniques.repositories.LigneAttributRepository;
import com.telephone.backendlignestelephoniques.repositories.LigneTelephoniqueRepository;
import com.telephone.backendlignestelephoniques.repositories.TypeLigneRepository;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
import com.telephone.backendlignestelephoniques.services.LigneTelephonique.LigneTelephoniqueService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class TypeLigneServiceImpl implements TypeLigneService {

    private final TypeLigneRepository typeLigneRepository;
    private final HistoriqueService historiqueService;
    private final LigneTelephoniqueService ligneTelephoniqueService;
    private final AttributRepository attributRepository;
    private final LigneTelephoniqueRepository ligneTelephoniqueRepository;
    private LigneAttributRepository ligneAttributRepository;

    @Override
    public void saveTypeLigne(TypeLigne typeLigne, String operateur) {
        if (typeLigneRepository.existsByNomType(typeLigne.getNomType())) {
            throw new IllegalArgumentException("Un type du même nom existe déjà.");
        }
        typeLigne.setCreatedDate(new Date());
        associateAttributesWithType(typeLigne);
        typeLigneRepository.save(typeLigne);
        historiqueService.saveHistoriques("Ajout [TypeLigne]", typeLigne.getNomType(), operateur);
    }

    private void associateAttributesWithType(TypeLigne typeLigne) {
        Set<Attribut> newAttributsSet = new HashSet<>();
        for (Attribut attribut : typeLigne.getAttributs()) {
            Optional<Attribut> existingAttribut = attributRepository.findById(attribut.getIdAttribut());
            existingAttribut.ifPresent(newAttributsSet::add);
        }
        typeLigne.setAttributs(newAttributsSet);
    }

    @Override
    public void updateTypeLigne(TypeLigne newTypeLigne, String operateur) {
        if (newTypeLigne.getIdType() == null) {
            throw new IllegalArgumentException("L'ID de TypeLigne ne doit pas être null");
        }
        TypeLigne existingTypeLigne = findExistingTypeLigne(newTypeLigne.getIdType());

        //updateBasicInfo
        existingTypeLigne.getAttributs().clear();
        existingTypeLigne.setAttributs(new HashSet<>(newTypeLigne.getAttributs()));

        //updateAssociatedAttributes
        existingTypeLigne.getAttributs().clear();
        existingTypeLigne.setAttributs(new HashSet<>(newTypeLigne.getAttributs()));

        TypeLigne updatedTypeLigne = typeLigneRepository.save(existingTypeLigne);

        updateAssociatedLines(updatedTypeLigne);
        historiqueService.saveHistoriques("Mise à jour [Type-Ligne]", updatedTypeLigne.getNomType(), operateur);
    }

    private void updateAssociatedLines(TypeLigne updatedTypeLigne) {
        // Trouver toutes les lignes associées à ce type de ligne
        Set<LigneTelephonique> associatedLines = ligneTelephoniqueRepository.findLigneTelephoniqueByTypeId(updatedTypeLigne.getIdType());
        System.out.println("\n\n\n\n\n associatedLines---"+associatedLines+"\n\n\n\n\n");
        for (LigneTelephonique ligne : associatedLines) {
            // Étape 1: Supprimer les attributs qui ne sont plus associés
            Set<Long> idAttributs = updatedTypeLigne.getAttributs().stream()
                    .map(Attribut::getIdAttribut)
                    .collect(Collectors.toSet());
            removeUnassociatedAttributes(ligne, idAttributs);
            // Étape 2: Ajouter de nouveaux attributs si nécessaire
            addNewAttributes(ligne, updatedTypeLigne);
            // Étape 3: Sauvegarder les modifications
            ligneTelephoniqueRepository.save(ligne);
        }
    }
    private void removeUnassociatedAttributes(LigneTelephonique ligne, Set<Long> attributSet) {
        System.out.println("\n\n\n\n\n removeUnassociatedAttributes---" + ligne + "\n\n\n\n\n");
        Iterator<LigneAttribut> iterator = ligne.getLigneAttributs().iterator();

        while (iterator.hasNext()) {
            LigneAttribut ligneAttribut = iterator.next();
            // Supprimez le LigneAttribut si son attribut n'est pas dans le Set attributSet
            if (!attributSet.contains(ligneAttribut.getAttribut().getIdAttribut())) {
                ligneAttributRepository.deleteById(ligneAttribut.getId());
                iterator.remove(); // Supprime cet élément de la liste ligne.getLigneAttributs()
            }
        }
    }
    private void addNewAttributes(LigneTelephonique ligne, TypeLigne updatedTypeLigne) {
        for (Attribut attribut : updatedTypeLigne.getAttributs()) {
            Optional<LigneAttribut> optionalLigneAttribut = ligne.getLigneAttributs().stream()
                    .filter(ligneAttribut -> ligneAttribut.getAttribut().equals(attribut))
                    .findFirst();

            if (optionalLigneAttribut.isEmpty()) {
                LigneAttribut newLigneAttribut = new LigneAttribut();
                newLigneAttribut.setAttribut(attribut);
                newLigneAttribut.setLigneId(ligne.getIdLigne());
                newLigneAttribut.setValeurAttribut(null);  // Valeur par défaut à null

                ligne.getLigneAttributs().add(newLigneAttribut);
                System.out.println("\n\n\n\n\n addNewAttributes---"+ligne+"\n\n\n\n\n");
            }
        }
    }

    private TypeLigne findExistingTypeLigne(Long id) {
        return typeLigneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Type-Ligne introuvable"));
    }

    @Override
    public TypeLigne getTypeLigne(Long typeLigneId) throws ElementNotFoundException {
        return typeLigneRepository.findById(typeLigneId)
                .orElseThrow(() -> new ElementNotFoundException("TypeLigne non trouvé"));
    }

    @Override
    public String getTypeLigneNomType(Long typeLigneId) throws ElementNotFoundException {
        TypeLigne typeLigne = typeLigneRepository.findById(typeLigneId)
                .orElseThrow(() -> new ElementNotFoundException("TypeLigne non trouvé"));
        return typeLigne.getNomType();
    }

    @Override
    public void deleteTypeLigne(Long id, String operateur) throws ElementNotFoundException {
        TypeLigne typeLigne = typeLigneRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("TypeLigne avec id " + id + " introuvable"));

        Set<LigneTelephonique> associatedLines = ligneTelephoniqueRepository.findLigneTelephoniqueByTypeId(id);
        for (LigneTelephonique telephonique: associatedLines){
            ligneTelephoniqueService.deleteLigneTelephonique(telephonique.getIdLigne(), operateur);
        }

        typeLigneRepository.delete(typeLigne);
        historiqueService.saveHistoriques("Suppression [Type-Ligne]", typeLigne.getNomType(), operateur);
    }

    @Override
    public List<TypeLigne> listTypeLigne() {
        return typeLigneRepository.findAll();
    }

    @Override
    public Page<TypeLigne> listTypeLignePage(int page, int size, String kw) {
        Pageable pageable = PageRequest.of(page, size);
        return typeLigneRepository.getAllTypeLignes(kw, pageable);
    }


}

