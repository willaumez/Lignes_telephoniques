package com.telephone.backendlignestelephoniques.services.Direction;

import com.telephone.backendlignestelephoniques.entities.Direction;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;

import java.util.List;

public interface DirectionService {


    //=============================================  Direction  ========================================================//
    void saveDirection(Direction direction, String operateur);
    Direction getDirection(Long directionId) throws ElementNotFoundException;
    void deleteDirection(Long id, String operateur) throws ElementNotFoundException;
    Direction updateDirection(Direction direction, String operateur);
    List<Direction> listDirections();

}
