package com.telephone.backendlignestelephoniques.services.TypeLigne;

import com.telephone.backendlignestelephoniques.entities.LigneTelephonique;
import com.telephone.backendlignestelephoniques.entities.TypeLigne;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
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
                .orElseThrow(() -> new ElementNotFoundException("TypeLigne with id " + id + " not found"));

        List<LigneTelephonique> ligneTelephoniques = ligneTelephoniqueService.ligneByType(typeLigne);
        for (LigneTelephonique ligneTelephonique : ligneTelephoniques) {
            ligneTelephoniqueService.deleteLigneTelephonique(ligneTelephonique.getIdLigne(), operateur);
        }

        typeLigneRepository.deleteById(id);
        historiqueService.saveHistoriques("Suppression [TypeLigne]", typeLigne.getNomType(), operateur);
    }

    @Override
    public TypeLigne updateTypeLigne(TypeLigne typeLigne, String operateur) {
        TypeLigne existingTypeLigne = typeLigneRepository.findById(typeLigne.getIdType())
                .orElseThrow(() -> new EntityNotFoundException("TypeLigne not found"));
        typeLigne.setCreatedDate(existingTypeLigne.getCreatedDate());
        TypeLigne updatedTypeLigne = typeLigneRepository.save(typeLigne);
        historiqueService.saveHistoriques("Mise à jour [TypeLigne]", updatedTypeLigne.getNomType(), operateur);
        return updatedTypeLigne;
    }

    @Override
    public List<TypeLigne> listTypeLigne() {
        return typeLigneRepository.findAll();
    }
}
