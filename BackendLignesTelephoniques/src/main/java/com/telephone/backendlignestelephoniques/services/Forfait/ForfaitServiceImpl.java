package com.telephone.backendlignestelephoniques.services.Forfait;

import com.telephone.backendlignestelephoniques.entities.Forfait;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.ForfaitRepository;
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
public class ForfaitServiceImpl implements ForfaitService {

    private ForfaitRepository forfaitRepository;
    private HistoriqueService historiqueService;

    @Override
    public void saveForfait(Forfait forfait) {
        forfaitRepository.save(forfait);
        historiqueService.saveHistoriques("Ajout [Forfait]", forfait.getNomForfait());
    }

    @Override
    public Forfait getForfait(Long forfaitId) throws ElementNotFoundException {
        return forfaitRepository.findById(forfaitId)
                .orElseThrow(() -> new ElementNotFoundException("----- Forfait non trouvé -----"));
    }

    @Override
    public void deleteForfait(Long id) throws ElementNotFoundException {
        Forfait forfait = forfaitRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Forfait with id " + id + " not found"));
        forfaitRepository.deleteById(id);
        historiqueService.saveHistoriques("Suppression [Forfait]", forfait.getNomForfait());
    }

    @Override
    public Forfait updateForfait(Forfait forfait) {
        Forfait updateForfait = forfaitRepository.save(forfait);
        historiqueService.saveHistoriques("Mise à jour [Forfait]", updateForfait.getNomForfait());
        return updateForfait;
    }

    @Override
    public List<Forfait> listForfaits() {
        return forfaitRepository.findAll();
    }
}
