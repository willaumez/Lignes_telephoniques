package com.telephone.backendlignestelephoniques.services.Corbeille;

import com.telephone.backendlignestelephoniques.dtos.LigneAttributDto;
import com.telephone.backendlignestelephoniques.embeddable.AttributValeur;
import com.telephone.backendlignestelephoniques.entities.*;
import com.telephone.backendlignestelephoniques.enums.EtatType;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.mappers.LigneMappers;
import com.telephone.backendlignestelephoniques.repositories.AttributRepository;
import com.telephone.backendlignestelephoniques.repositories.CorbeilleRepository;
import com.telephone.backendlignestelephoniques.repositories.TypeLigneRepository;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
import com.telephone.backendlignestelephoniques.services.LigneTelephonique.LigneTelephoniqueService;
import com.telephone.backendlignestelephoniques.services.LigneTelephonique.LigneTelephoniqueServiceImpl;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
