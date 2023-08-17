package com.telephone.backendlignestelephoniques.services.TypeLigne;

import com.telephone.backendlignestelephoniques.entities.TypeLigne;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.TypeLigneRepository;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class TypeLigneServiceImpl implements TypeLigneService {

    private TypeLigneRepository typeLigneRepository;
    private HistoriqueService historiqueService;

    @Override
    public void saveTypeLigne(TypeLigne typeLigne) {
        typeLigneRepository.save(typeLigne);
        historiqueService.saveHistoriques("Ajout [TypeLigne]", typeLigne.getNomType());
    }

    @Override
    public TypeLigne getTypeLigne(Long typeLigneId) throws ElementNotFoundException {
        return typeLigneRepository.findById(typeLigneId)
                .orElseThrow(() -> new ElementNotFoundException("----- TypeLigne non trouvé -----"));
    }

    @Override
    public void deleteTypeLigne(Long id) throws ElementNotFoundException {
        TypeLigne typeLigne = typeLigneRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("TypeLigne with id " + id + " not found"));
        typeLigneRepository.deleteById(id);
        historiqueService.saveHistoriques("Suppression [TypeLigne]", typeLigne.getNomType());
    }

    @Override
    public TypeLigne updateTypeLigne(TypeLigne typeLigne) {
        TypeLigne updatedTypeLigne = typeLigneRepository.save(typeLigne);
        historiqueService.saveHistoriques("Mise à jour [TypeLigne]", updatedTypeLigne.getNomType());
        return updatedTypeLigne;
    }

    @Override
    public List<TypeLigne> listTypeLigne() {
        return typeLigneRepository.findAll();
    }
}
