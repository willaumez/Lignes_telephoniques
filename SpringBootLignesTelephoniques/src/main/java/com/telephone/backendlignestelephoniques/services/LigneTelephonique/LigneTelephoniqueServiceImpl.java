package com.telephone.backendlignestelephoniques.services.LigneTelephonique;

import com.telephone.backendlignestelephoniques.entities.*;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.*;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class LigneTelephoniqueServiceImpl implements LigneTelephoniqueService {

    private LigneTelephoniqueRepository ligneTelephoniqueRepository;
    private HistoriqueService historiqueService;
    private TypeLigneRepository typeLigneRepository;

    @Override
    public void saveLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur) {
        ligneTelephonique.setCreatedDate(new Date());

        TypeLigne typeLigne = typeLigneRepository.findByNomType(ligneTelephonique.getTypeLigne().getNomType());
        ligneTelephonique.setTypeLigne(typeLigne);

        ligneTelephoniqueRepository.save(ligneTelephonique);
        historiqueService.saveHistoriques("Ajout [Ligne-Téléphonique]", ligneTelephonique.getNumeroLigne(), operateur);
    }

    @Override
    public LigneTelephonique getLigneTelephonique(Long ligneId) throws ElementNotFoundException {
        return ligneTelephoniqueRepository.findById(ligneId)
                .orElseThrow(() -> new ElementNotFoundException("Ligne-Téléphonique introuvable"));
    }

    @Override
    public void deleteLigneTelephonique(Long id, String operateur) throws ElementNotFoundException {
        LigneTelephonique ligneTelephonique = ligneTelephoniqueRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Ligne-Téléphonique avec id " + id + " introuvable"));

        ligneTelephoniqueRepository.deleteById(id);
        historiqueService.saveHistoriques("Suppression [Ligne-Téléphonique]", ligneTelephonique.getNumeroLigne(), operateur);
    }

    @Override
    public LigneTelephonique updateLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur) {
        LigneTelephonique existingLigne = ligneTelephoniqueRepository.findById(ligneTelephonique.getIdLigne())
                .orElseThrow(() -> new EntityNotFoundException("Ligne-Téléphonique not found"));
        ligneTelephonique.setCreatedDate(existingLigne.getCreatedDate());
        LigneTelephonique updatedLigne = ligneTelephoniqueRepository.save(ligneTelephonique);
        historiqueService.saveHistoriques("Mise à jour [Ligne-Téléphonique]", updatedLigne.getNumeroLigne(), operateur);
        return updatedLigne;
    }

    @Override
    public List<LigneTelephonique> listLigneTelephonique() {
        return ligneTelephoniqueRepository.findAll();
    }

    @Override
    public List<LigneTelephonique> listLigneTelephoniqueByType(TypeLigne typeLigne) {
        Set<LigneTelephonique> lignesTelephoniques = typeLigne.getLignesTelephoniques();
        return new ArrayList<>(lignesTelephoniques);
    }

}
