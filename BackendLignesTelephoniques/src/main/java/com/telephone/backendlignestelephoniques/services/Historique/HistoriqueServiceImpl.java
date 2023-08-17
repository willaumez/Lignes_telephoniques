package com.telephone.backendlignestelephoniques.services.Historique;

import com.telephone.backendlignestelephoniques.entities.Historiques;
import com.telephone.backendlignestelephoniques.entities.Restoration;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.HistoriquesRepository;
import com.telephone.backendlignestelephoniques.services.User.UserServices;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class HistoriqueServiceImpl implements HistoriqueService {

    private HistoriquesRepository historiquesRepository;
    //private UserServices userServices;

    @Override
    public void saveHistoriques(String action, String elementCible) {

        Historiques historique = new Historiques();
        //UserDetails userDetails = userServices.getLoggedInUserDetails();
        //LocalDateTime localDateTime = LocalDateTime.now();

        historique.setActionEffectue(action);
        //historique.setDateAction(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        historique.setDateAction(new Date());
        historique.setNomOperateur("userDetails.getUsername()");
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
    public List<Historiques> listHistoriques() {
        return historiquesRepository.findAll();
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
