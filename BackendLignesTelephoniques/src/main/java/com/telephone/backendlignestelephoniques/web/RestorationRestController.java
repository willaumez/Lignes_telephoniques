package com.telephone.backendlignestelephoniques.web;

import com.telephone.backendlignestelephoniques.entities.Restoration;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.services.Restoration.RestorationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class RestorationRestController {
    private RestorationService restorationService;


    //====================  list  ======================//
    @GetMapping("/listRestorations")
    public List<Restoration> listRestoration() {
        return restorationService.listRestoration();
    }

    //====================  get  ======================//
    @GetMapping("/restoration/{restorationId}")
    public Restoration getRestoration(@PathVariable Long restorationId) throws ElementNotFoundException {
        return restorationService.getRestoration(restorationId);
    }

    //====================  save  ======================//
    @PostMapping("/restoration/save")
    public Restoration saveRestoration(@RequestBody Restoration restoration){
        this.restorationService.saveRestoration(restoration);
        return restoration;
    }

    //====================  delete  ======================//
    @DeleteMapping("/restoration/delete/{id}")
    public void deleteRestoration(@PathVariable Long id){
        this.restorationService.deleteRestoration(id);
    }

    //====================  update  ======================//
    @PutMapping("/restoration/update")
    public Restoration updateRestoration(@RequestBody Restoration restoration) {
        return restorationService.updateRestoration(restoration);
    }



}
