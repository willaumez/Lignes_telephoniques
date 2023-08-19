package com.telephone.backendlignestelephoniques.web;

import com.telephone.backendlignestelephoniques.entities.Corbeille;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.services.Corbeille.CorbeilleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class RestorationRestController {
    private CorbeilleService corbeilleService;


    //====================  list  ======================//
    @GetMapping("/listRestorations")
    public List<Corbeille> listRestoration() {
        return corbeilleService.listRestoration();
    }

    //====================  get  ======================//
    @GetMapping("/restoration/{restorationId}")
    public Corbeille getRestoration(@PathVariable Long restorationId) throws ElementNotFoundException {
        return corbeilleService.getRestoration(restorationId);
    }

    //====================  save  ======================//
    @PostMapping("/restoration/save/{operateur}")
    public Corbeille saveRestoration(@PathVariable String operateur, @RequestBody Corbeille corbeille){
        this.corbeilleService.saveRestoration(corbeille, operateur);
        return corbeille;
    }

    //====================  delete  ======================//
    @DeleteMapping("/restoration/delete/{id}/{operateur}")
    public void deleteRestoration(@PathVariable Long id, @PathVariable String operateur){
        this.corbeilleService.deleteRestoration(id, operateur);
    }

    //====================  restoration  ======================//
    @GetMapping("/restorer/{restorationId}/{operateur}")
    public void setRestoration(@PathVariable Long restorationId, @PathVariable String operateur) throws ElementNotFoundException {
         corbeilleService.restorer(restorationId, operateur);
    }

}
