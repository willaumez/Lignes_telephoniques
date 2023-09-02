package com.telephone.backendlignestelephoniques.services.Corbeille;

import com.telephone.backendlignestelephoniques.entities.*;
import com.telephone.backendlignestelephoniques.repositories.CorbeilleRepository;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CorbeilleServiceImpl implements CorbeilleService {

    private CorbeilleRepository corbeilleRepository;
    private HistoriqueService historiqueService;

    @Override
    public void deleteOldRestorations() {
        Date currentDate = new Date();
        Date thresholdDate = new Date(currentDate.getTime() - 7 * 24 * 60 * 60 * 1000); // 7 days in milliseconds

        List<Corbeille> oldCorbeilles = corbeilleRepository.findByDateSuppressionBefore(thresholdDate);
        corbeilleRepository.deleteAll(oldCorbeilles);

        historiqueService.saveHistoriques("Suppression liste-Corbeille", "Suppression automatique après 7 jours", "Serveur");
    }

    //Execution automatique
    @Scheduled(cron = "0 0 * * *") // Exécute tous les jours à minuit
    public void deleteOldRestorationsScheduled() {
        deleteOldRestorations();
    }


}
