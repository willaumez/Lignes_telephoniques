package com.telephone.backendlignestelephoniques.web;


import com.telephone.backendlignestelephoniques.entities.Fonction;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.services.Fonction.FonctionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class FonctionRestController {

    private FonctionService fonctionService;

    //====================  listFonctions  ======================//
    @GetMapping("/fonctions")
    public List<Fonction> listFonctions() {
        return fonctionService.listFonctions();
    }

    //====================  une fonction  ======================//
    @GetMapping("/fonction/{fonctionId}")
    public Fonction getFonction(@PathVariable Long fonctionId) throws ElementNotFoundException {
        return fonctionService.getFonction(fonctionId);
    }

    //====================  save  ======================//
    @PostMapping("/fonction/save/{operateur}")
    public Fonction saveFonction(@PathVariable String operateur, @RequestBody Fonction fonction){
        this.fonctionService.saveFonction(fonction, operateur);
        return fonction;
    }

    //====================  delete  ======================//
    @DeleteMapping("/fonction/delete/{id}/{operateur}")
    public void deleteFonction(@PathVariable Long id, @PathVariable String operateur) throws ElementNotFoundException {
        this.fonctionService.deleteFonction(id, operateur);
    }

    //====================  update  ======================//
    @PutMapping("/fonction/update/{operateur}")
    public Fonction updateDirection(@PathVariable String operateur, @RequestBody Fonction fonction) {
        return fonctionService.updateFonction(fonction, operateur);
    }



}
