package com.telephone.backendlignestelephoniques.services.Direction;

import com.telephone.backendlignestelephoniques.entities.Direction;
import com.telephone.backendlignestelephoniques.entities.LigneTelephonique;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.DirectionRepository;
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
public class DirectionServiceImpl implements DirectionService {

    private DirectionRepository directionRepository;
    private HistoriqueService historiqueService;
    private LigneTelephoniqueService ligneTelephoniqueService;

    @Override
    public void saveDirection(Direction direction, String operateur) {
        direction.setCreatedDate(new Date());
        directionRepository.save(direction);
        historiqueService.saveHistoriques("Ajout [Direction]", direction.getNomDirection(), operateur);
    }

    @Override
    public Direction getDirection(Long directionId) throws ElementNotFoundException {
        return directionRepository.findById(directionId)
                .orElseThrow(() -> new ElementNotFoundException("----- Direction non trouvé -----"));
    }

    @Override
    public void deleteDirection(Long id, String operateur) throws ElementNotFoundException {
        Direction direction = directionRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Direction with id " + id + " not found"));

        List<LigneTelephonique> ligneTelephoniques = ligneTelephoniqueService.ligneByDirection(direction);
        for (LigneTelephonique ligneTelephonique : ligneTelephoniques) {
            ligneTelephoniqueService.deleteLigneTelephonique(ligneTelephonique.getIdLigne(), operateur);
        }

        directionRepository.deleteById(id);
        historiqueService.saveHistoriques("Suppression [Direction]", direction.getNomDirection(), operateur);
    }

    @Override
    public Direction updateDirection(Direction direction, String operateur) {
        Direction existingDirection = directionRepository.findById(direction.getIdDirection())
                .orElseThrow(() -> new EntityNotFoundException("Direction not found"));
        direction.setCreatedDate(existingDirection.getCreatedDate());
        Direction updatedDirection = directionRepository.save(direction);
        historiqueService.saveHistoriques("Mise à jour [Direction]", updatedDirection.getNomDirection(), operateur);

        return updatedDirection;
    }

    @Override
    public List<Direction> listDirections() {
        return directionRepository.findAll();
    }


}
