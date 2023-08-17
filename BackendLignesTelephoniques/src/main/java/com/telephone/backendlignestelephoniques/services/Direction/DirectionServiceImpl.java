package com.telephone.backendlignestelephoniques.services.Direction;

import com.telephone.backendlignestelephoniques.entities.Direction;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.DirectionRepository;
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
public class DirectionServiceImpl implements DirectionService {

    private DirectionRepository directionRepository;
    private HistoriqueService historiqueService;

    @Override
    public void saveDirection(Direction direction) {
        directionRepository.save(direction);
        historiqueService.saveHistoriques("Ajout [Direction]", direction.getNomDirection());
    }

    @Override
    public Direction getDirection(Long directionId) throws ElementNotFoundException {
        return directionRepository.findById(directionId)
                .orElseThrow(() -> new ElementNotFoundException("----- Direction non trouvé -----"));
    }

    @Override
    public void deleteDirection(Long id) throws ElementNotFoundException {
        Direction direction = directionRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Direction with id " + id + " not found"));

        directionRepository.deleteById(id);
        historiqueService.saveHistoriques("Suppression [Direction]", direction.getNomDirection());
    }

    @Override
    public Direction updateDirection(Direction direction) {
        Direction updatedDirection = directionRepository.save(direction);
        historiqueService.saveHistoriques("Mise à jour [Direction]", updatedDirection.getNomDirection());

        return updatedDirection;
    }

    @Override
    public List<Direction> listDirections() {
        return directionRepository.findAll();
    }


}
