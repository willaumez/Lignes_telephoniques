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
    public TypeLigne saveTypeLigne(@PathVariable String operateur, @RequestBody TypeLigne typeLigne){
        this.typeLigneService.saveTypeLigne(typeLigne, operateur);
        return typeLigne;
    }

    //====================  delete  ======================//
    @DeleteMapping("/delete/{id}/{operateur}")
    public ResponseEntity<String> deleteTypeLigne(@PathVariable Long id, @PathVariable String operateur) {
        try {
            typeLigneService.deleteTypeLigne(id, operateur);
            return ResponseEntity.ok("Type-Ligne Supprimé avec succés! ");
        } catch (ElementNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //====================  update  ======================//
    @PutMapping("/update/{operateur}")
    public TypeLigne updateTypeLigne(@PathVariable String operateur, @RequestBody TypeLigne typeLigne) {
        return typeLigneService.updateTypeLigne(typeLigne, operateur);
    }

}
