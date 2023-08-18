package com.telephone.backendlignestelephoniques.web;

import com.telephone.backendlignestelephoniques.entities.LigneTelephonique;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.services.LigneTelephonique.LigneTelephoniqueService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class LigneTelephoniqueRestController {

    private LigneTelephoniqueService ligneTelephoniqueService;

    //====================  list ======================//
    @GetMapping("/telephonique")
    public List<LigneTelephonique> listLigneTelephoniques() {
        return ligneTelephoniqueService.listLigneTelephonique();
    }

    //====================  get  ======================//
    @GetMapping("/telephonique/{telephoniqueId}")
    public LigneTelephonique getLigneTelephonique(@PathVariable Long telephoniqueId) throws ElementNotFoundException {
        return ligneTelephoniqueService.getLigneTelephonique(telephoniqueId);
    }

    //====================  save  ======================//
    @PostMapping("/telephonique/save/{operateur}")
    public LigneTelephonique saveLigneTelephonique(@PathVariable String operateur, @RequestBody LigneTelephonique telephonique){
        this.ligneTelephoniqueService.saveLigneTelephonique(telephonique, operateur);
        return telephonique;
    }

    //====================  delete  ======================//
    @DeleteMapping("/telephonique/delete/{id}/{operateur}")
    public void deleteLigneTelephonique(@PathVariable Long id, @PathVariable String operateur) throws ElementNotFoundException {
        this.ligneTelephoniqueService.deleteLigneTelephonique(id, operateur);
    }

    //====================  update  ======================//
    @PutMapping("/telephonique/update/{operateur}")
    public LigneTelephonique updateLigneTelephonique(@PathVariable String operateur, @RequestBody LigneTelephonique telephonique) {
        return ligneTelephoniqueService.updateLigneTelephonique(telephonique, operateur);
    }



}
