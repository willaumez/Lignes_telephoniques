package com.telephone.backendlignestelephoniques.web;

import com.nimbusds.jose.shaded.gson.Gson;
import com.telephone.backendlignestelephoniques.dtos.LigneTelephoniqueDto;
import com.telephone.backendlignestelephoniques.entities.LigneTelephonique;
import com.telephone.backendlignestelephoniques.entities.TypeLigne;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.services.LigneTelephonique.LigneTelephoniqueService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/telephonique")
@Slf4j
@CrossOrigin("*")
public class LigneTelephoniqueRestController {

    private LigneTelephoniqueService ligneTelephoniqueService;

    //====================  list ======================//
    @GetMapping
    public List<LigneTelephoniqueDto> listLigneTelephoniques() {
        return ligneTelephoniqueService.listLigneTelephonique();
    }

    //====================  get  ======================//
    @GetMapping("/{telephoniqueId}")
    public LigneTelephonique getLigneTelephonique(@PathVariable Long telephoniqueId) throws ElementNotFoundException {
        return ligneTelephoniqueService.getLigneTelephonique(telephoniqueId);
    }

    //====================  save  ======================//
    @PostMapping("/save/{operateur}")
    public void saveLigneTelephonique(@PathVariable String operateur, @RequestBody LigneTelephonique telephonique) throws ElementNotFoundException {
        Gson gson = new Gson();
        System.out.println("saveLigneTelephonique " + gson.toJson(telephonique));
        this.ligneTelephoniqueService.saveLigneTelephonique(telephonique, operateur);
    }

    //====================  delete  ======================//
    @DeleteMapping("/delete/{id}/{operateur}")
    public void deleteLigneTelephonique(@PathVariable Long id, @PathVariable String operateur) throws ElementNotFoundException {
        this.ligneTelephoniqueService.deleteLigneTelephonique(id, operateur);
    }

    //====================  update  ======================//
    @PutMapping("/update/{operateur}")
    public LigneTelephonique updateLigneTelephonique(@PathVariable String operateur, @RequestBody LigneTelephonique telephonique) {
        return ligneTelephoniqueService.updateLigneTelephonique(telephonique, operateur);
    }

    //====================  Lignes par type  ======================//
    @GetMapping("/type/{typeId}")
    public List<LigneTelephonique> listLigneTelephoniqueByType(@PathVariable Long typeId){
        return ligneTelephoniqueService.listLigneTelephoniqueByType(typeId);
    }


}
