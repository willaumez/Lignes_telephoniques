package com.telephone.backendlignestelephoniques.web;

import com.telephone.backendlignestelephoniques.entities.Corbeille;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.services.Corbeille.CorbeilleService;
import com.telephone.backendlignestelephoniques.services.LigneTelephonique.LigneTelephoniqueService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/corbeille")
@Slf4j
@CrossOrigin("*")
public class CorbeilleRestController {
    private LigneTelephoniqueService corbeilleService;

    //====================  list  ======================//
    @GetMapping("/listCorbeille")
    public List<Corbeille> listRestoration() {
        return corbeilleService.listCorbeille();
    }

    //====================  get  ======================//
    @GetMapping("/{restorationId}")
    public Corbeille getRestoration(@PathVariable Long restorationId) throws ElementNotFoundException {
        return corbeilleService.getElementCorbeille(restorationId);
    }

    //====================  save  ======================//
    /*@PostMapping("/restoration/save/{operateur}")
    public Corbeille saveRestoration(@PathVariable String operateur, @RequestBody Corbeille corbeille){
        this.corbeilleService.saveInCorbeille(corbeille, operateur);
        return corbeille;
    }*/

    //====================  delete  ======================//
    @DeleteMapping("/delete/{id}/{operateur}")
    public void deleteRestoration(@PathVariable Long id, @PathVariable String operateur) throws ElementNotFoundException {
        this.corbeilleService.deleteFromCorbeille(id, operateur);
    }

    //====================  restoration  ======================//
    @GetMapping("/restorer/{restorationId}/{operateur}")
    public void setRestoration(@PathVariable Long restorationId, @PathVariable String operateur) throws ElementNotFoundException {
         corbeilleService.restorationOfElement(restorationId, operateur);
    }

}
