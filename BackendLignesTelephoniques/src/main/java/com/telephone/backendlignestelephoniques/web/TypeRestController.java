package com.telephone.backendlignestelephoniques.web;

import com.telephone.backendlignestelephoniques.entities.TypeLigne;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.services.TypeLigne.TypeLigneService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class TypeRestController {

    private TypeLigneService typeLigneService;

    //====================  list  ======================//
    @GetMapping("/typeLigne")
    public List<TypeLigne> listTypeLigne() {
        return typeLigneService.listTypeLigne();
    }

    //====================  get  ======================//
    @GetMapping("/typeLigne/{typeId}")
    public TypeLigne getTypeLigne(@PathVariable Long typeId) throws ElementNotFoundException {
        return typeLigneService.getTypeLigne(typeId);
    }

    //====================  save  ======================//
    @PostMapping("/typeLigne/save/{operateur}")
    public TypeLigne saveTypeLigne(@PathVariable String operateur, @RequestBody TypeLigne typeLigne){
        this.typeLigneService.saveTypeLigne(typeLigne, operateur);
        return typeLigne;
    }

    //====================  delete  ======================//
    @DeleteMapping("/typeLigne/delete/{id}/{operateur}")
    public void deleteTypeLigne(@PathVariable Long id, @PathVariable String operateur) throws ElementNotFoundException {
        this.typeLigneService.deleteTypeLigne(id, operateur);
    }

    //====================  update  ======================//
    @PutMapping("/typeLigne/update/{operateur}")
    public TypeLigne updateTypeLigne(@PathVariable String operateur, @RequestBody TypeLigne typeLigne) {
        return typeLigneService.updateTypeLigne(typeLigne, operateur);
    }



}
