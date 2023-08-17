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
    @PostMapping("/typeLigne/save")
    public TypeLigne saveTypeLigne(@RequestBody TypeLigne typeLigne){
        this.typeLigneService.saveTypeLigne(typeLigne);
        return typeLigne;
    }

    //====================  delete  ======================//
    @DeleteMapping("/typeLigne/delete/{id}")
    public void deleteTypeLigne(@PathVariable Long id) throws ElementNotFoundException {
        this.typeLigneService.deleteTypeLigne(id);
    }

    //====================  update  ======================//
    @PutMapping("/typeLigne/update")
    public TypeLigne updateTypeLigne(@RequestBody TypeLigne typeLigne) {
        return typeLigneService.updateTypeLigne(typeLigne);
    }



}
