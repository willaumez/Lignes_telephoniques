package com.telephone.backendlignestelephoniques.services.Forfait;

import com.telephone.backendlignestelephoniques.entities.Forfait;
import com.telephone.backendlignestelephoniques.entities.LigneTelephonique;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.ForfaitRepository;
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
public class ForfaitServiceImpl implements ForfaitService {

    private ForfaitRepository forfaitRepository;
    private HistoriqueService historiqueService;
    private LigneTelephoniqueService ligneTelephoniqueService;

    @Override
    public void saveForfait(Forfait forfait, String operateur) {
        forfait.setCreatedDate(new Date());
        forfaitRepository.save(forfait);
        historiqueService.saveHistoriques("Ajout [Forfait]", forfait.getNomForfait(), operateur);
    }

    @Override
    public Forfait getForfait(Long forfaitId) throws ElementNotFoundException {
        return forfaitRepository.findById(forfaitId)
                .orElseThrow(() -> new ElementNotFoundException("----- Forfait non trouvé -----"));
    }

    @Override
    public void deleteForfait(Long id, String operateur) throws ElementNotFoundException {
        Forfait forfait = forfaitRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Forfait with id " + id + " not found"));

        List<LigneTelephonique> ligneTelephoniques = ligneTelephoniqueService.ligneByForfait(forfait);
        for (LigneTelephonique ligneTelephonique : ligneTelephoniques) {
            ligneTelephoniqueService.deleteLigneTelephonique(ligneTelephonique.getIdLigne(), operateur);
        }

        forfaitRepository.deleteById(id);
        historiqueService.saveHistoriques("Suppression [Forfait]", forfait.getNomForfait(), operateur);
    }

    @Override
    public Forfait updateForfait(Forfait forfait, String operateur) {
        Forfait existingForfait = forfaitRepository.findById(forfait.getIdForfait())
                .orElseThrow(() -> new EntityNotFoundException("Forfait not found"));
        forfait.setCreatedDate(existingForfait.getCreatedDate());
        Forfait updateForfait = forfaitRepository.save(forfait);
        historiqueService.saveHistoriques("Mise à jour [Forfait]", updateForfait.getNomForfait(), operateur);
        return updateForfait;
    }

    @Override
    public List<Forfait> listForfaits() {
        return forfaitRepository.findAll();
    }
}
