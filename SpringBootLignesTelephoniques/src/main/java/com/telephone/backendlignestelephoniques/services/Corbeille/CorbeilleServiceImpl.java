package com.telephone.backendlignestelephoniques.services.Corbeille;

import com.telephone.backendlignestelephoniques.entities.LigneTelephonique;
import com.telephone.backendlignestelephoniques.entities.Corbeille;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.CorbeilleRepository;
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
public class CorbeilleServiceImpl implements CorbeilleService {

    //@Autowired
    private CorbeilleRepository corbeilleRepository;
    private LigneTelephoniqueService ligneTelephoniqueService;
    private HistoriqueService historiqueService;

    @Override
    public void saveRestoration(Corbeille corbeille, String operateur) {
        corbeille.setDateSuppression(new Date());
        corbeilleRepository.save(corbeille);
    }

    @Override
    public Corbeille getRestoration(Long restorationId) throws ElementNotFoundException {
        return corbeilleRepository.findById(restorationId)
                .orElseThrow(() -> new ElementNotFoundException("----- Corbeille non trouvée -----"));
    }

    @Override
    public void deleteRestoration(Long id, String operateur) {
        corbeilleRepository.deleteById(id);
    }

    @Override
    public void restorer(Long id, String operateur) throws ElementNotFoundException {
        Corbeille corbeille = corbeilleRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Corbeille with id " + id + " not found"));

        Corbeille.Ligne restorationLigne = corbeille.getLigneTelephonique();
        LigneTelephonique ligneTelephonique = new LigneTelephonique();
        BeanUtils.copyProperties(restorationLigne, ligneTelephonique);

        ligneTelephoniqueService.saveLigneTelephonique(ligneTelephonique, operateur);
        deleteRestoration(id, operateur);

        historiqueService.saveHistoriques("Corbeille de la ligne", ligneTelephonique.getNumeroLigne(), operateur);
    }

    @Override
    public List<Corbeille> listRestoration() {
        return corbeilleRepository.findAll();
    }


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
