package com.telephone.backendlignestelephoniques.services.Direction;

import com.telephone.backendlignestelephoniques.entities.Direction;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
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
    @Override
    public void saveDirection(Direction direction) {
    }

    @Override
    public Direction getDirection(Long directionId) throws ElementNotFoundException {
        return null;
    }

    @Override
    public void deleteDirection(Long id) {

    }

    @Override
    public Direction updateDirection(Direction direction) {
        return null;
    }

    @Override
    public List<Direction> listDirections() {
        return null;
    }
}
