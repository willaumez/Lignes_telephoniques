package com.telephone.backendlignestelephoniques.web;

import com.telephone.backendlignestelephoniques.entities.Corbeille;
import com.telephone.backendlignestelephoniques.entities.Historiques;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.services.Corbeille.CorbeilleService;
import com.telephone.backendlignestelephoniques.services.LigneTelephonique.LigneTelephoniqueService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/corbeille")
@Slf4j
@CrossOrigin("*")
public class CorbeilleRestController {
    private LigneTelephoniqueService corbeilleService;

    //====================  list  ======================//
    @GetMapping("/listCorbeille")
    public List<Corbeille> listCorbeille() {
        return corbeilleService.listCorbeille();
    }
    @GetMapping
    public ResponseEntity<Map<String, Object>> listCorbeillePage(@RequestParam(name = "page", defaultValue = "0") int page,
                                                               @RequestParam(name = "size", defaultValue = "10") int size,
                                                               @RequestParam(name = "kw", defaultValue = "") String kw) {
        Page<Corbeille> corbeillePage = corbeilleService.listCorbeillePage(page, size, kw);
        List<Corbeille> corbeilles = corbeillePage.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("dataElements", corbeilles);
        response.put("currentPage", corbeillePage.getNumber());
        response.put("totalItems", corbeillePage.getTotalElements());
        response.put("totalPages", corbeillePage.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //====================  get  ======================//
    @GetMapping("/{restorationId}")
    public Corbeille getCorbeille(@PathVariable Long restorationId) throws ElementNotFoundException {
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
    public void deleteCorbeille(@PathVariable Long id, @PathVariable String operateur) throws ElementNotFoundException {
        this.corbeilleService.deleteFromCorbeille(id, operateur);
    }

    @DeleteMapping("/delete/all/{operateur}")
    public void deleteCorbeilleAll(@PathVariable String operateur) throws ElementNotFoundException {
        this.corbeilleService.deleteAllCorbeille(operateur);
    }

    //====================  restoration  ======================//
    @GetMapping("/restaurer/{restorationId}/{operateur}")
    public void setRestoration(@PathVariable Long restorationId, @PathVariable String operateur) throws ElementNotFoundException {
         corbeilleService.restorationOfElement(restorationId, operateur);
    }

    @GetMapping("/restaurer/all/{operateur}")
    public ResponseEntity<Map<String, Integer>> setRestorationAll(@PathVariable String operateur) {
        try {
            Map<String, Integer> result = corbeilleService.restorationAllElement(operateur);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
