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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/telephonique")
@Slf4j
@CrossOrigin("*")
public class LigneTelephoniqueRestController {

    private LigneTelephoniqueService ligneTelephoniqueService;

    //====================  list ======================//
    /*@GetMapping
    public List<LigneTelephonique> listLigneTelephoniques() {
        return ligneTelephoniqueService.listLigneTelephonique();
    }
*/
    @GetMapping
    public ResponseEntity<Map<String, Object>> listLigneTelephoniques(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                      @RequestParam(name = "size", defaultValue = "10") int size,
                                                                      @RequestParam(name = "kw", defaultValue = "") String kw) {
        Page<LigneTelephonique> pageLigneTelephonique = ligneTelephoniqueService.listLigneTelephonique(page, size, kw);

        List<LigneTelephonique> ligneTelephoniques = pageLigneTelephonique.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("dataElements", ligneTelephoniques);
        response.put("currentPage", pageLigneTelephonique.getNumber());
        response.put("totalItems", pageLigneTelephonique.getTotalElements());
        response.put("totalPages", pageLigneTelephonique.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
        //return ligneTelephoniqueService.listLigneTelephonique();
    }

    //====================  Lignes par type  ======================//
    @GetMapping("/typeId/{typeId}")
    public ResponseEntity<Map<String, Object>> listLigneTelephoniqueByTypePage(@PathVariable Long typeId,
                                                                               @RequestParam(name = "page", defaultValue = "0") int page,
                                                                               @RequestParam(name = "size", defaultValue = "10") int size,
                                                                               @RequestParam(name = "kw", defaultValue = "") String kw) {
        Page<LigneTelephonique> pageLigneTelephonique = ligneTelephoniqueService.listLigneTelephoniqueByType(page, size, kw, typeId);

        List<LigneTelephonique> ligneTelephoniques = pageLigneTelephonique.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("dataElements", ligneTelephoniques);
        response.put("currentPage", pageLigneTelephonique.getNumber());
        response.put("totalItems", pageLigneTelephonique.getTotalElements());
        response.put("totalPages", pageLigneTelephonique.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
        //return ligneTelephoniqueService.listLigneTelephoniqueByType(typeId);
    }
    @GetMapping("/type/{typeId}")
    public List<LigneTelephonique> listLigneTelephoniqueByType(@PathVariable Long typeId) {
        return ligneTelephoniqueService.listLigneTelephoniqueByType(typeId);
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

    //====================  get Rapprochement  ======================//
    @GetMapping("/rapprochement")
    public List<Rapprochement> rapprochementList() {
        return ligneTelephoniqueService.rapprochementList();
    }




    //====================  Importation des donnée  ======================//
    @PostMapping("/import/{operateur}")
    public ResponseEntity<Map<String, Object>> importLigneTelephonique(@PathVariable String operateur, @RequestBody LigneTelephonique[] telephonique) throws ElementNotFoundException {
        System.out.println("\n\n\n\n\n importLigneTelephonique     "+ Arrays.toString(telephonique) +"\n\n\n\n");
        Map<String, Object> response = ligneTelephoniqueService.importLigneTelephonique(telephonique, operateur);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //Accueil
    @GetMapping("/accueil")
    public ResponseEntity<Map<String, Object>> importAccueil(){
        Map<String, Object> response = ligneTelephoniqueService.importAccueil();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
