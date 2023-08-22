package com.telephone.backendlignestelephoniques.web;

import com.telephone.backendlignestelephoniques.entities.Historiques;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class HistoriqueRestController {

    private HistoriqueService historiqueService;

    //====================  listHistoriques ======================//
    @GetMapping("/historiques")
    public List<Historiques> listHistoriques() {
        return historiqueService.listHistoriques();
    }

    //====================  un historique  ======================//
    @GetMapping("/historiques/{historiqueId}")
    public Historiques getHistorique(@PathVariable Long historiqueId) throws ElementNotFoundException {
        return historiqueService.getHistoriques(historiqueId);
    }

    //====================  save  ======================//
/*
    @PostMapping("/historiques/save")
    public Historiques saveHistorique(@RequestBody Historiques historiques){
        this.historiqueService.saveHistoriques(historiques);
        return historiques;
    }
*/

    //====================  delete  ======================//
    @DeleteMapping("/historiques/delete/{id}")
    public void deleteHistorique(@PathVariable Long id){
        this.historiqueService.deleteHistoriques(id);
    }
    //====================  delete all  ======================//
    @DeleteMapping("/historiques/delete/all/{operateur}")
    public void deleteAllHistorique(@PathVariable String operateur){
        this.historiqueService.deleteAllHistoriques(operateur);
    }

    //====================  update  ======================//



}
