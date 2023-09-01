package com.telephone.backendlignestelephoniques.web;

import com.telephone.backendlignestelephoniques.entities.Attribut;
import com.telephone.backendlignestelephoniques.entities.TypeLigne;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.services.TypeLigne.TypeLigneService;
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
@RequestMapping("/typeLigne")
@Slf4j
@CrossOrigin("*")
public class TypeRestController {

    private TypeLigneService typeLigneService;

    //====================  list  ======================//
    @GetMapping("all")
    public List<TypeLigne> listTypeLigne() {
        return typeLigneService.listTypeLigne();
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listTypeLignePage(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                 @RequestParam(name = "size", defaultValue = "10") int size,
                                                                 @RequestParam(name = "kw", defaultValue = "") String kw) {
        Page<TypeLigne> pageTypeLigne = typeLigneService.listTypeLignePage(page, size, kw);

        List<TypeLigne> typeLignes = pageTypeLigne.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("dataElements", typeLignes);
        response.put("currentPage", pageTypeLigne.getNumber());
        response.put("totalItems", pageTypeLigne.getTotalElements());
        response.put("totalPages", pageTypeLigne.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //====================  get  ======================//
    @GetMapping("/{typeId}")
    public ResponseEntity<TypeLigne> getTypeLigne(@PathVariable Long typeId) {
        try {
            TypeLigne typeLigne = typeLigneService.getTypeLigne(typeId);
            return ResponseEntity.ok(typeLigne);
        } catch (ElementNotFoundException e) {
            log.error("TypeLigne not found", e);
            return ResponseEntity.notFound().build();
        }
    }

    //====================  save  ======================//
    @PostMapping("/save/{operateur}")
    public void saveTypeLigne(@PathVariable String operateur, @RequestBody TypeLigne typeLigne) {
        typeLigneService.saveTypeLigne(typeLigne, operateur);
    }

    //====================  delete  ======================//
    @DeleteMapping("/delete/{id}/{operateur}")
    public void deleteTypeLigne(@PathVariable Long id, @PathVariable String operateur) {
        try {
            typeLigneService.deleteTypeLigne(id, operateur);
        } catch (ElementNotFoundException e) {
            log.error("Failed to delete TypeLigne", e);
        }
    }

    //====================  update  ======================//
    @PutMapping("/update/{operateur}")
    public void updateTypeLigne(@PathVariable String operateur, @RequestBody TypeLigne typeLigne) {
        log.info("Updating TypeLigne: {}", typeLigne);
        typeLigneService.updateTypeLigne(typeLigne, operateur);
    }

}
