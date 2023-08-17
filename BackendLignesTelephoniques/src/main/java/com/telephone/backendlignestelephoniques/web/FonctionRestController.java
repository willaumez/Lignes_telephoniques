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
    @PostMapping("/fonction/save")
    public Fonction saveFonction(@RequestBody Fonction fonction){
        this.fonctionService.saveFonction(fonction);
        return fonction;
    }

    //====================  delete  ======================//
    @DeleteMapping("/fonction/delete/{id}")
    public void deleteFonction(@PathVariable Long id) throws ElementNotFoundException {
        this.fonctionService.deleteFonction(id);
    }

    //====================  update  ======================//
    @PutMapping("/fonction/update")
    public Fonction updateDirection(@RequestBody Fonction fonction) {
        return fonctionService.updateFonction(fonction);
    }



}
