package com.telephone.backendlignestelephoniques.web;

import com.telephone.backendlignestelephoniques.entities.Forfait;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.services.Forfait.ForfaitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class ForfaitRestController {

    private ForfaitService forfaitService;

    //====================  listFonctions  ======================//
    @GetMapping("/forfait")
    public List<Forfait> listForfaits() {
        return forfaitService.listForfaits();
    }

    //====================  une fonction  ======================//
    @GetMapping("/forfait/{forfaitId}")
    public Forfait getForfait(@PathVariable Long forfaitId) throws ElementNotFoundException {
        return forfaitService.getForfait(forfaitId);
    }

    //====================  save  ======================//
    @PostMapping("/forfait/save/{operateur}")
    public Forfait saveForfait(@PathVariable String operateur, @RequestBody Forfait forfait){
        this.forfaitService.saveForfait(forfait, operateur);
        return forfait;
    }

    //====================  delete  ======================//
    @DeleteMapping("/forfait/delete/{id}/{operateur}")
    public void deleteForfait(@PathVariable Long id, @PathVariable String operateur) throws ElementNotFoundException {
        this.forfaitService.deleteForfait(id, operateur);
    }

    //====================  update  ======================//
    @PutMapping("/forfait/update/{operateur}")
    public Forfait updateDirection(@PathVariable String operateur, @RequestBody Forfait forfait) {
        return forfaitService.updateForfait(forfait, operateur);
    }



}
