package com.telephone.backendlignestelephoniques.services.TypeLigne;

import com.telephone.backendlignestelephoniques.entities.Attribut;
import com.telephone.backendlignestelephoniques.entities.LigneTelephonique;
import com.telephone.backendlignestelephoniques.entities.TypeLigne;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.AttributRepository;
import com.telephone.backendlignestelephoniques.repositories.TypeLigneRepository;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
import com.telephone.backendlignestelephoniques.services.LigneTelephonique.LigneTelephoniqueService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class TypeLigneServiceImpl implements TypeLigneService {

    private TypeLigneRepository typeLigneRepository;
    private HistoriqueService historiqueService;
    private LigneTelephoniqueService ligneTelephoniqueService;
    private AttributRepository attributRepository;

    @Override
    public void saveTypeLigne(TypeLigne typeLigne, String operateur) {
        if (typeLigneRepository.existsByNomType(typeLigne.getNomType())){
            throw new IllegalArgumentException("Un type du même nom existe déjà.");
        }
        typeLigne.setCreatedDate(new Date());
        // Associer les attributs au type de ligne
        associateAttributesWithType(typeLigne);
        typeLigneRepository.save(typeLigne);
        historiqueService.saveHistoriques("Ajout [TypeLigne]", typeLigne.getNomType(), operateur);
    }
    private void associateAttributesWithType(TypeLigne typeLigne) {
        List<Attribut> attributs = new ArrayList<>(typeLigne.getAttributs());
        typeLigne.getAttributs().clear();
        for (Attribut attribut : attributs) {
            if (attribut.getIdAttribut() != null) {
                typeLigne.getAttributs().add(attributRepository.findById(attribut.getIdAttribut()).orElse(null));
            }
        }
    }

    @Override
    public void updateTypeLigne(TypeLigne typeLigne, String operateur) {
        System.out.println(typeLigne);
        TypeLigne existingTypeLigne = typeLigneRepository.findById(typeLigne.getIdType())
                .orElseThrow(() -> new EntityNotFoundException("Type-Ligne introuvable"));
        existingTypeLigne.setNomType(typeLigne.getNomType()); // Mettre à jour le nom si nécessaire
        existingTypeLigne.setCreatedDate(existingTypeLigne.getCreatedDate());

        // Associer les attributs au type de ligne
        existingTypeLigne.getAttributs().clear();
        existingTypeLigne.setAttributs(new HashSet<>(typeLigne.getAttributs()));
        associateAttributesWithType(existingTypeLigne);

        TypeLigne updatedTypeLigne = typeLigneRepository.save(existingTypeLigne);
        historiqueService.saveHistoriques("Mise à jour [Type-Ligne]", updatedTypeLigne.getNomType(), operateur);
    }


    @Override
    public TypeLigne getTypeLigne(Long typeLigneId) throws ElementNotFoundException {
        return typeLigneRepository.findById(typeLigneId)
                .orElseThrow(() -> new ElementNotFoundException("----- TypeLigne non trouvé -----"));
    }


    @Override
    public void deleteTypeLigne(Long id, String operateur) throws ElementNotFoundException {
        TypeLigne typeLigne = typeLigneRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("TypeLigne avec id " + id + " introuvable"));
        System.out.println("deleteTypeLigne--  "+typeLigne);

        typeLigneRepository.delete(typeLigne);
        historiqueService.saveHistoriques("Suppression [Type-Ligne]", typeLigne.getNomType(), operateur);
    }

    @Override
    public List<TypeLigne> listTypeLigne() {
        return typeLigneRepository.findAll();
    }

    @Override
    public void associateAttributesWithType(Long typeLigneId, List<Long> attributIds) throws ElementNotFoundException {
        TypeLigne typeLigne = typeLigneRepository.findById(typeLigneId)
                .orElseThrow(() -> new ElementNotFoundException("Type-Ligne introuvable"));

        // Récupérez les objets Attribut en utilisant leurs IDs
        List<Attribut> attributs = attributRepository.findAllById(attributIds);

        // Associez les attributs au type de ligne
        typeLigne.getAttributs().addAll(attributs);

        typeLigneRepository.save(typeLigne);
    }


    // Ajoutez une méthode pour associer une liste d'énumérations à un attribut
    /*public void associateEnumerationWithAttribut(Long typeLigneId, Long attributId, List<String> enumeration) throws ElementNotFoundException {
        TypeLigne typeLigne = typeLigneRepository.findById(typeLigneId)
                .orElseThrow(() -> new ElementNotFoundException("TypeLigne not found"));
        Attribut attribut = typeLigne.getAttributs().stream()
                .filter(a -> a.getIdAttribut().equals(attributId))
                .findFirst()
                .orElseThrow(() -> new ElementNotFoundException("Attribut not found in the specified TypeLigne"));

        attribut.setEnumeration(enumeration);
        typeLigneRepository.save(typeLigne);
    }*/


}
