package com.telephone.backendlignestelephoniques.web;

import com.telephone.backendlignestelephoniques.entities.Direction;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.services.Direction.DirectionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class DirectionRestController {

    private DirectionService directionService;

    //====================  list Directions  ======================//
    @GetMapping("/listDirections")
    public List<Direction> listDirections() {
        return directionService.listDirections();
    }

    //====================  une Direction  ======================//
    @GetMapping("/direction/{directionId}")
    public Direction getDirection(@PathVariable Long directionId) throws ElementNotFoundException {
        return directionService.getDirection(directionId);
    }

    //====================  save  ======================//
    @PostMapping("/direction/save/{operateur}")
    public Direction saveDirection(@PathVariable String operateur, @RequestBody Direction direction){
        this.directionService.saveDirection(direction, operateur);
        return direction;
    }

    //====================  delete  ======================//
    @DeleteMapping("/direction/delete/{id}/{operateur}")
    public void deleteDirection(@PathVariable Long id, @PathVariable String operateur) throws ElementNotFoundException {
        this.directionService.deleteDirection(id, operateur);
    }

    //====================  update  ======================//
    @PutMapping("/direction/update/{operateur}")
    public Direction updateDirection(@PathVariable String operateur, @RequestBody Direction direction) {
        return directionService.updateDirection(direction, operateur);
    }



}
