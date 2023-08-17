package com.telephone.backendlignestelephoniques.services.LigneTelephonique;

import com.telephone.backendlignestelephoniques.entities.LigneTelephonique;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.LigneTelephoniqueRepository;
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
public class LigneTelephoniqueServiceImpl implements LigneTelephoniqueService {

    private LigneTelephoniqueRepository ligneTelephoniqueRepository;
    private HistoriqueService historiqueService;

    @Override
    public void saveLigneTelephonique(LigneTelephonique ligneTelephonique) {
        ligneTelephoniqueRepository.save(ligneTelephonique);
        historiqueService.saveHistoriques("Ajout [Ligne-Téléphonique]", ligneTelephonique.getNumeroLigne());
    }

    @Override
    public LigneTelephonique getLigneTelephonique(Long ligneId) throws ElementNotFoundException {
        return ligneTelephoniqueRepository.findById(ligneId)
                .orElseThrow(() -> new ElementNotFoundException("----- LigneTelephonique non trouvé -----"));
    }

    @Override
    public void deleteLigneTelephonique(Long id) throws ElementNotFoundException {
        LigneTelephonique ligneTelephonique = ligneTelephoniqueRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Ligne-Telephonique with id " + id + " not found"));
        ligneTelephoniqueRepository.deleteById(id);
        historiqueService.saveHistoriques("Suppression [Ligne-Téléphonique]", ligneTelephonique.getNumeroLigne());
    }

    @Override
    public LigneTelephonique updateLigneTelephonique(LigneTelephonique ligneTelephonique) {
        LigneTelephonique updatedLigne = ligneTelephoniqueRepository.save(ligneTelephonique);
        historiqueService.saveHistoriques("Mise à jour [Ligne-Téléphonique]", updatedLigne.getNumeroLigne());
        return updatedLigne;
    }

    @Override
    public List<LigneTelephonique> listLigneTelephonique() {
        return ligneTelephoniqueRepository.findAll();
    }


}
