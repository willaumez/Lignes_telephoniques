package com.telephone.backendlignestelephoniques.web;

import com.telephone.backendlignestelephoniques.entities.Historiques;
import com.telephone.backendlignestelephoniques.entities.User;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
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
@Slf4j
@CrossOrigin("*")
public class HistoriqueRestController {

    private HistoriqueService historiqueService;

    //====================  listHistoriques ======================//
    @GetMapping("/historiques")
    public ResponseEntity<Map<String, Object>> listHistoriques(@RequestParam(name = "page", defaultValue = "0") int page,
                                                               @RequestParam(name = "size", defaultValue = "10") int size,
                                                               @RequestParam(name = "kw", defaultValue = "") String kw) {
        Page<Historiques> pageHistoriques = historiqueService.listHistoriques(page, size, kw);
        List<Historiques> historiques = pageHistoriques.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("dataElements", historiques);
        response.put("currentPage", pageHistoriques.getNumber());
        response.put("totalItems", pageHistoriques.getTotalElements());
        response.put("totalPages", pageHistoriques.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /*public List<Historiques> listHistoriques() {
        return historiqueService.listHistoriques();
    }*/

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
