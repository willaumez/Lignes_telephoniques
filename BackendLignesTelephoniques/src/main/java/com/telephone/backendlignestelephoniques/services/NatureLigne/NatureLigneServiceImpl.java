package com.telephone.backendlignestelephoniques.services.NatureLigne;

import com.telephone.backendlignestelephoniques.entities.NatureLigne;
import com.telephone.backendlignestelephoniques.entities.TypeLigne;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.NatureRepository;
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
public class NatureLigneServiceImpl implements NatureLigneService {

    private NatureRepository natureRepository;
    private HistoriqueService historiqueService;

    @Override
    public void saveNatureLigne(NatureLigne natureLigne) {
        natureRepository.save(natureLigne);
        historiqueService.saveHistoriques("Ajout [Nature-Ligne]", natureLigne.getNomNature());
    }

    @Override
    public NatureLigne getNatureLigne(Long natureId) throws ElementNotFoundException {
        return natureRepository.findById(natureId)
                .orElseThrow(() -> new ElementNotFoundException("----- Nature-Ligne non trouvé -----"));
    }

    @Override
    public void deleteNatureLigne(Long id) throws ElementNotFoundException {
        NatureLigne natureLigne = natureRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("NatureLigne with id " + id + " not found"));
        natureRepository.deleteById(id);
        historiqueService.saveHistoriques("Suppression [Nature-Ligne]", natureLigne.getNomNature());
    }

    @Override
    public NatureLigne updateNatureLigne(NatureLigne natureLigne) {
        NatureLigne updatedNature = natureRepository.save(natureLigne);
        historiqueService.saveHistoriques("Mise à jour [Nature-Ligne]", updatedNature.getNomNature());
        return updatedNature;
    }

    @Override
    public List<NatureLigne> listNatureLigne() {
        return natureRepository.findAll();
    }
}
