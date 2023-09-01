package com.telephone.backendlignestelephoniques.web;

import com.telephone.backendlignestelephoniques.entities.Attribut;
import com.telephone.backendlignestelephoniques.entities.User;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.services.Attribut.AttributService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Attr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/attributs")
@AllArgsConstructor
@Slf4j
public class AttributRestController {

    private AttributService attributService;

    @PostMapping("/save/{operateur}")
    public void saveAttribut(@RequestBody Attribut attribut, @PathVariable String operateur) {
        attributService.saveAttribut(attribut, operateur);
    }

    @GetMapping("/{attributId}")
    public ResponseEntity<Attribut> getAttribut(@PathVariable Long attributId) {
        try {
            Attribut attribut = attributService.getAttribut(attributId);
            return ResponseEntity.ok(attribut);
        } catch (ElementNotFoundException e) {
            log.error("Attribut not found", e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{attributId}/{operateur}")
    public void deleteAttribut(@PathVariable Long attributId, @PathVariable String operateur) {
        try {
            attributService.deleteAttribut(attributId, operateur);
        } catch (ElementNotFoundException e) {
            log.error("Failed to delete attribut", e);
        }
    }

    @PutMapping("/update/{operateur}")
    public void updateAttribut(@PathVariable String operateur, @RequestBody Attribut attribut) throws ElementNotFoundException {
        attributService.updateAttribut(attribut, operateur);
    }

    @GetMapping("/all")
    public List<Attribut> listAttributs() {
        return attributService.listAttribut();
    }
    @GetMapping
    public ResponseEntity<Map<String, Object>> listAttributsPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                 @RequestParam(name = "size", defaultValue = "10") int size,
                                                                 @RequestParam(name = "kw", defaultValue = "") String kw) {
        Page<Attribut> pageAttribut = attributService.listAttributsPage(page, size, kw);

        List<Attribut> attributs = pageAttribut.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("dataElements", attributs);
        response.put("currentPage", pageAttribut.getNumber());
        response.put("totalItems", pageAttribut.getTotalElements());
        response.put("totalPages", pageAttribut.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/names")
    public Set<String> listAttributNames() {
        return attributService.listAttributNames();
    }

    @GetMapping("/names/{typeId}")
    public Set<String> listAttributNames(@PathVariable Long typeId) throws ElementNotFoundException {
        return attributService.listAttributNamesByType(typeId);
    }


}
