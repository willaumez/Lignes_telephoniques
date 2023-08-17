package com.telephone.backendlignestelephoniques.services.Restoration;

import com.telephone.backendlignestelephoniques.entities.Restoration;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.RestorationRepository;
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
public class RestorationServiceImpl implements RestorationService {

    //@Autowired
    private RestorationRepository restorationRepository;
    @Override
    public void saveRestoration(Restoration restoration) {
    }

    @Override
    public Restoration getRestoration(Long restorationId) throws ElementNotFoundException {
        return null;
    }

    @Override
    public void deleteRestoration(Long id) {

    }

    @Override
    public Restoration updateRestoration(Restoration restoration) {
        return null;
    }

    @Override
    public List<Restoration> listRestoration() {
        return null;
    }


    @Override
    public void deleteOldRestorations() {
        Date currentDate = new Date();
        Date thresholdDate = new Date(currentDate.getTime() - 7 * 24 * 60 * 60 * 1000); // 7 days in milliseconds

        List<Restoration> oldRestorations = restorationRepository.findByDateSuppressionBefore(thresholdDate);
        restorationRepository.deleteAll(oldRestorations);
    }

    //Execution automatique
    @Scheduled(cron = "0 0 * * *") // Exécute tous les jours à minuit
    public void deleteOldRestorationsScheduled() {
        deleteOldRestorations();
    }

}
