package com.telephone.backendlignestelephoniques.services.LigneTelephonique;

import com.telephone.backendlignestelephoniques.entities.*;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.LigneTelephoniqueRepository;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
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
public class LigneTelephoniqueServiceImpl implements LigneTelephoniqueService {

    private LigneTelephoniqueRepository ligneTelephoniqueRepository;
    private HistoriqueService historiqueService;

    @Override
    public void saveLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur) {
        ligneTelephonique.setCreatedDate(new Date());
        ligneTelephoniqueRepository.save(ligneTelephonique);
        historiqueService.saveHistoriques("Ajout [Ligne-Téléphonique]", ligneTelephonique.getNumeroLigne(), operateur);
    }

    @Override
    public LigneTelephonique getLigneTelephonique(Long ligneId) throws ElementNotFoundException {
        return ligneTelephoniqueRepository.findById(ligneId)
                .orElseThrow(() -> new ElementNotFoundException("----- LigneTelephonique non trouvé -----"));
    }

    @Override
    public void deleteLigneTelephonique(Long id, String operateur) throws ElementNotFoundException {
        LigneTelephonique ligneTelephonique = ligneTelephoniqueRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Ligne-Telephonique with id " + id + " not found"));
        ligneTelephoniqueRepository.deleteById(id);
        historiqueService.saveHistoriques("Suppression [Ligne-Téléphonique]", ligneTelephonique.getNumeroLigne(), operateur);
    }

    @Override
    public LigneTelephonique updateLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur) {
        LigneTelephonique existingLigne = ligneTelephoniqueRepository.findById(ligneTelephonique.getIdLigne())
                .orElseThrow(() -> new EntityNotFoundException("LigneTelephonique not found"));
        ligneTelephonique.setCreatedDate(existingLigne.getCreatedDate());
        LigneTelephonique updatedLigne = ligneTelephoniqueRepository.save(ligneTelephonique);
        historiqueService.saveHistoriques("Mise à jour [Ligne-Téléphonique]", updatedLigne.getNumeroLigne(), operateur);
        return updatedLigne;
    }

    @Override
    public List<LigneTelephonique> listLigneTelephonique() {
        return ligneTelephoniqueRepository.findAll();
    }

    // find By
    @Override
    public List<LigneTelephonique> ligneByDebit(Debit debit) {
        return ligneTelephoniqueRepository.findByDebit(debit);
    }

    @Override
    public List<LigneTelephonique> ligneByDirection(Direction direction) {
        return ligneTelephoniqueRepository.findByDirection(direction);
    }

    @Override
    public List<LigneTelephonique> ligneByFonction(Fonction fonction) {
        return ligneTelephoniqueRepository.findByFonction(fonction);
    }

    @Override
    public List<LigneTelephonique> ligneByForfait(Forfait forfait) {
        return ligneTelephoniqueRepository.findByForfait(forfait);
    }

    @Override
    public List<LigneTelephonique> ligneByNature(NatureLigne natureLigne) {
        return ligneTelephoniqueRepository.findByNatureLigne(natureLigne);
    }

    @Override
    public List<LigneTelephonique> ligneByType(TypeLigne typeLigne) {
        return ligneTelephoniqueRepository.findByTypeLigne(typeLigne);
    }


}
