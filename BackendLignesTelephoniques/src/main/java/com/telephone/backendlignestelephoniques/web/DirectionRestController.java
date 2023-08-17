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
    @PostMapping("/direction/save")
    public Direction saveDirection(@RequestBody Direction direction){
        this.directionService.saveDirection(direction);
        return direction;
    }

    //====================  delete  ======================//
    @DeleteMapping("/direction/delete/{id}")
    public void deleteDirection(@PathVariable Long id) throws ElementNotFoundException {
        this.directionService.deleteDirection(id);
    }

    //====================  update  ======================//
    @PutMapping("/direction/update")
    public Direction updateDirection(@RequestBody Direction direction) {
        return directionService.updateDirection(direction);
    }



}
