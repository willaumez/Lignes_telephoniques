package com.telephone.backendlignestelephoniques.services.Historique;

import com.telephone.backendlignestelephoniques.entities.Historiques;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.HistoriquesRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class HistoriqueServiceImpl implements HistoriqueService {

    private HistoriquesRepository historiquesRepository;


    @Override
    public void saveHistoriques(String action, String elementCible, String operateur) {

        Historiques historique = new Historiques();
        //LocalDateTime localDateTime = LocalDateTime.now();

        historique.setActionEffectue(action);
        //historique.setDateAction(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        historique.setDateAction(new Date());
        historique.setNomOperateur(operateur);
        historique.setElementCible(elementCible);

        historiquesRepository.save(historique);
    }

    @Override
    public Historiques getHistoriques(Long historiqueId) throws ElementNotFoundException {
        return historiquesRepository.findById(historiqueId)
                .orElseThrow(() -> new ElementNotFoundException("----- Historique non trouvé -----"));
    }

    @Override
    public void deleteHistoriques(Long id) {
        historiquesRepository.deleteById(id);
    }
    @Override
    public void deleteAllHistoriques(String operateur) {
        historiquesRepository.deleteAll();
        saveHistoriques("Suppression des historiques", "Tous les historiques", operateur);
    }

    @Override
    public Page<Historiques> listHistoriques(int page, int size, String kw) {
        Pageable pageable = PageRequest.of(page, size);
        return historiquesRepository.getAllHistoriques(kw, pageable);
    }

    @Override
    public void deleteOldHistoriques() {
        Date currentDate = new Date();
        Date thresholdDate = new Date(currentDate.getTime() - 10 * 24 * 60 * 60 * 1000); // 10 days in milliseconds

        List<Historiques> oldHistoriques = historiquesRepository.findByDateActionBefore(thresholdDate);
        historiquesRepository.deleteAll(oldHistoriques);
    }

    //Execution automatique
    @Scheduled(cron = "0 0 * * *") // Exécute tous les jours à minuit
    public void deleteOldHistoriquesScheduled() {
        deleteOldHistoriques();
    }


}
