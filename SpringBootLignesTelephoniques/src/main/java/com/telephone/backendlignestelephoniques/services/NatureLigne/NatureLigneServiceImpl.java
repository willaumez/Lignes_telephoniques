package com.telephone.backendlignestelephoniques.services.NatureLigne;

import com.telephone.backendlignestelephoniques.entities.LigneTelephonique;
import com.telephone.backendlignestelephoniques.entities.NatureLigne;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.NatureRepository;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
import com.telephone.backendlignestelephoniques.services.LigneTelephonique.LigneTelephoniqueService;
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
public class NatureLigneServiceImpl implements NatureLigneService {

    private NatureRepository natureRepository;
    private HistoriqueService historiqueService;
    private LigneTelephoniqueService ligneTelephoniqueService;


    @Override
    public void saveNatureLigne(NatureLigne natureLigne, String operateur) {
        natureLigne.setCreatedDate(new Date());
        natureRepository.save(natureLigne);
        historiqueService.saveHistoriques("Ajout [Nature-Ligne]", natureLigne.getNomNature(), operateur);
    }

    @Override
    public NatureLigne getNatureLigne(Long natureId) throws ElementNotFoundException {
        return natureRepository.findById(natureId)
                .orElseThrow(() -> new ElementNotFoundException("----- Nature-Ligne non trouvé -----"));
    }

    @Override
    public void deleteNatureLigne(Long id, String operateur) throws ElementNotFoundException {
        NatureLigne natureLigne = natureRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("NatureLigne with id " + id + " not found"));

        List<LigneTelephonique> ligneTelephoniques = ligneTelephoniqueService.ligneByNature(natureLigne);
        for (LigneTelephonique ligneTelephonique : ligneTelephoniques) {
            ligneTelephoniqueService.deleteLigneTelephonique(ligneTelephonique.getIdLigne(), operateur);
        }

        natureRepository.deleteById(id);
        historiqueService.saveHistoriques("Suppression [Nature-Ligne]", natureLigne.getNomNature(), operateur);
    }

    @Override
    public NatureLigne updateNatureLigne(NatureLigne natureLigne, String operateur) {
        NatureLigne existingNatureLigne = natureRepository.findById(natureLigne.getIdNature())
                .orElseThrow(() -> new EntityNotFoundException("NatureLigne not found"));
        natureLigne.setCreatedDate(existingNatureLigne.getCreatedDate());
        NatureLigne updatedNature = natureRepository.save(natureLigne);
        historiqueService.saveHistoriques("Mise à jour [Nature-Ligne]", updatedNature.getNomNature(), operateur);
        return updatedNature;
    }

    @Override
    public List<NatureLigne> listNatureLigne() {
        return natureRepository.findAll();
    }
}
