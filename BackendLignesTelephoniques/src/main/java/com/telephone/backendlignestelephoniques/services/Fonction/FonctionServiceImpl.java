package com.telephone.backendlignestelephoniques.services.Fonction;

import com.telephone.backendlignestelephoniques.entities.Direction;
import com.telephone.backendlignestelephoniques.entities.Fonction;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.FonctionRepository;
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
public class FonctionServiceImpl implements FonctionService {

    private FonctionRepository fonctionRepository;
    private HistoriqueService historiqueService;

    @Override
    public void saveFonction(Fonction fonction) {
        fonctionRepository.save(fonction);
        historiqueService.saveHistoriques("Ajout [Fonction]", fonction.getNomFonction());
    }

    @Override
    public Fonction getFonction(Long fonctionId) throws ElementNotFoundException {
        return fonctionRepository.findById(fonctionId)
                .orElseThrow(() -> new ElementNotFoundException("----- Fonction non trouvé -----"));
    }

    @Override
    public void deleteFonction(Long id) throws ElementNotFoundException {
        Fonction fonction = fonctionRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Fonction with id " + id + " not found"));
        fonctionRepository.deleteById(id);
        historiqueService.saveHistoriques("Suppression [Fonction]", fonction.getNomFonction());
    }

    @Override
    public Fonction updateFonction(Fonction fonction) {
        Fonction updateFonction = fonctionRepository.save(fonction);
        historiqueService.saveHistoriques("Mise à jour [Fonction]", updateFonction.getNomFonction());
        return updateFonction;
    }

    @Override
    public List<Fonction> listFonctions() {
        return fonctionRepository.findAll();
    }


}
