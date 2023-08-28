package com.telephone.backendlignestelephoniques.web;

import com.nimbusds.jose.shaded.gson.Gson;
import com.telephone.backendlignestelephoniques.dtos.LigneTelephoniqueDto;
import com.telephone.backendlignestelephoniques.entities.LigneTelephonique;
import com.telephone.backendlignestelephoniques.entities.Rapprochement;
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
    public List<LigneTelephonique> listLigneTelephoniques() {
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
        System.out.println("\n\n\n\n\n\nSauvegarde de la ligne téléphonique en cours...----- "+telephonique+"\n\n\n\n\n\n");
        ligneTelephoniqueService.saveLigneTelephonique(telephonique, operateur);
    }

    //====================  delete  ======================//
    @DeleteMapping("/delete/{id}/{operateur}")
    public void deleteLigneTelephonique(@PathVariable Long id, @PathVariable String operateur) throws ElementNotFoundException {
        ligneTelephoniqueService.deleteLigneTelephonique(id, operateur);
    }

    //====================  update  ======================//
    @PutMapping("/update/{operateur}")
    public void updateLigneTelephonique(@PathVariable String operateur, @RequestBody LigneTelephonique telephonique) throws ElementNotFoundException {
        ligneTelephoniqueService.updateLigneTelephonique(telephonique, operateur);
    }

    //====================  Lignes par type  ======================//
    @GetMapping("/type/{typeId}")
    public List<LigneTelephonique> listLigneTelephoniqueByType(@PathVariable Long typeId) {
        return ligneTelephoniqueService.listLigneTelephoniqueByType(typeId);
    }

    //====================  get Rapprochement  ======================//
    @GetMapping("/rapprochement")
    public List<Rapprochement> rapprochementList() {
        return ligneTelephoniqueService.rapprochementList();
    }


}
