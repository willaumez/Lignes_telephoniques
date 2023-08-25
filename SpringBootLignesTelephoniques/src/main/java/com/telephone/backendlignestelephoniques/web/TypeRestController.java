package com.telephone.backendlignestelephoniques.web;

import com.telephone.backendlignestelephoniques.entities.TypeLigne;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.services.TypeLigne.TypeLigneService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/typeLigne")
@Slf4j
@CrossOrigin("*")
public class TypeRestController {

    private TypeLigneService typeLigneService;

    //====================  list  ======================//
    @GetMapping
    public List<TypeLigne> listTypeLigne() {
        return typeLigneService.listTypeLigne();
    }

    //====================  get  ======================//
    @GetMapping("/{typeId}")
    public TypeLigne getTypeLigne(@PathVariable Long typeId) throws ElementNotFoundException {
        return typeLigneService.getTypeLigne(typeId);
    }

    //====================  save  ======================//
    @PostMapping("/save/{operateur}")
    public void saveTypeLigne(@PathVariable String operateur, @RequestBody TypeLigne typeLigne){
        this.typeLigneService.saveTypeLigne(typeLigne, operateur);
    }

    //====================  delete  ======================//
    @DeleteMapping("/delete/{id}/{operateur}")
    public void deleteTypeLigne(@PathVariable Long id, @PathVariable String operateur) {
        try {
            typeLigneService.deleteTypeLigne(id, operateur);
        } catch (ElementNotFoundException e) {
        }
    }

    //====================  update  ======================//
    @PutMapping("/update/{operateur}")
    public void updateTypeLigne(@PathVariable String operateur, @RequestBody TypeLigne typeLigne) {
        System.out.println(typeLigne);
        typeLigneService.updateTypeLigne(typeLigne, operateur);
    }

}
