package com.telephone.backendlignestelephoniques.web;

import com.telephone.backendlignestelephoniques.entities.NatureLigne;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.services.NatureLigne.NatureLigneService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class NatureRestController {

    private NatureLigneService natureLigneService;

    //====================  list  ======================//
    @GetMapping("/nature")
    public List<NatureLigne> listNatureLignes() {
        return natureLigneService.listNatureLigne();
    }

    //====================  get  ======================//
    @GetMapping("/nature/{natureId}")
    public NatureLigne getNatureLigne(@PathVariable Long natureId) throws ElementNotFoundException {
        return natureLigneService.getNatureLigne(natureId);
    }

    //====================  save  ======================//
    @PostMapping("/nature/save/{operateur}")
    public NatureLigne saveNatureLigne(@PathVariable String operateur, @RequestBody NatureLigne natureLigne){
        this.natureLigneService.saveNatureLigne(natureLigne, operateur);
        return natureLigne;
    }

    //====================  delete  ======================//
    @DeleteMapping("/nature/delete/{id}/{operateur}")
    public void deleteNatureLigne(@PathVariable Long id, @PathVariable String operateur) throws ElementNotFoundException {
        this.natureLigneService.deleteNatureLigne(id, operateur);
    }

    //====================  update  ======================//
    @PutMapping("/nature/update/{operateur}")
    public NatureLigne updateNatureLigne(@PathVariable String operateur, @RequestBody NatureLigne natureLigne) {
        return natureLigneService.updateNatureLigne(natureLigne, operateur);
    }



}
