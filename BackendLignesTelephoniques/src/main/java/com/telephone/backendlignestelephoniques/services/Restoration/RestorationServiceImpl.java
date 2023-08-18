package com.telephone.backendlignestelephoniques.services.Restoration;

import com.telephone.backendlignestelephoniques.entities.LigneTelephonique;
import com.telephone.backendlignestelephoniques.entities.Restoration;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.RestorationRepository;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
import com.telephone.backendlignestelephoniques.services.LigneTelephonique.LigneTelephoniqueService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class RestorationServiceImpl implements RestorationService {

    //@Autowired
    private RestorationRepository restorationRepository;
    private LigneTelephoniqueService ligneTelephoniqueService;
    private HistoriqueService historiqueService;

    @Override
    public void saveRestoration(Restoration restoration, String operateur) {
        restoration.setDateSuppression(new Date());
        restorationRepository.save(restoration);
    }

    @Override
    public Restoration getRestoration(Long restorationId) throws ElementNotFoundException {
        return restorationRepository.findById(restorationId)
                .orElseThrow(() -> new ElementNotFoundException("----- Restoration non trouvée -----"));
    }

    @Override
    public void deleteRestoration(Long id, String operateur) {
        restorationRepository.deleteById(id);
    }

    @Override
    public void restorer(Long id, String operateur) throws ElementNotFoundException {
        Restoration restoration = restorationRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Restoration with id " + id + " not found"));

        Restoration.Ligne restorationLigne = restoration.getLigneTelephonique();
        LigneTelephonique ligneTelephonique = new LigneTelephonique();
        BeanUtils.copyProperties(restorationLigne, ligneTelephonique);

        ligneTelephoniqueService.saveLigneTelephonique(ligneTelephonique, operateur);
        deleteRestoration(id, operateur);

        historiqueService.saveHistoriques("Restoration de la ligne", ligneTelephonique.getNumeroLigne(), operateur);
    }

    @Override
    public List<Restoration> listRestoration() {
        return restorationRepository.findAll();
    }


    @Override
    public void deleteOldRestorations() {
        Date currentDate = new Date();
        Date thresholdDate = new Date(currentDate.getTime() - 7 * 24 * 60 * 60 * 1000); // 7 days in milliseconds

        List<Restoration> oldRestorations = restorationRepository.findByDateSuppressionBefore(thresholdDate);
        restorationRepository.deleteAll(oldRestorations);

        historiqueService.saveHistoriques("Suppression liste-Restoration", "Suppression automatique après 7 jours", "Serveur");
    }

    //Execution automatique
    @Scheduled(cron = "0 0 * * *") // Exécute tous les jours à minuit
    public void deleteOldRestorationsScheduled() {
        deleteOldRestorations();
    }


}
