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

import java.util.Date;
import java.util.List;

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
        typeLigne.setCreatedDate(new Date());
        typeLigneRepository.save(typeLigne);
        historiqueService.saveHistoriques("Ajout [TypeLigne]", typeLigne.getNomType(), operateur);
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

        List<LigneTelephonique> ligneTelephoniques = ligneTelephoniqueService.listLigneTelephoniqueByType(typeLigne);
        for (LigneTelephonique ligneTelephonique : ligneTelephoniques) {
            ligneTelephoniqueService.deleteLigneTelephonique(ligneTelephonique.getIdLigne(), operateur);
        }

        typeLigneRepository.deleteById(id);
        historiqueService.saveHistoriques("Suppression [Type-Ligne]", typeLigne.getNomType(), operateur);
    }

    @Override
    public TypeLigne updateTypeLigne(TypeLigne typeLigne, String operateur) {
        TypeLigne existingTypeLigne = typeLigneRepository.findById(typeLigne.getIdType())
                .orElseThrow(() -> new EntityNotFoundException("Type-Ligne introuvable"));
        typeLigne.setCreatedDate(existingTypeLigne.getCreatedDate());

        // Mettez à jour les attributs associés au type de ligne
        existingTypeLigne.getAttributs().clear();
        existingTypeLigne.getAttributs().addAll(typeLigne.getAttributs());

        TypeLigne updatedTypeLigne = typeLigneRepository.save(existingTypeLigne);
        historiqueService.saveHistoriques("Mise à jour [Type-Ligne]", updatedTypeLigne.getNomType(), operateur);
        return updatedTypeLigne;
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
