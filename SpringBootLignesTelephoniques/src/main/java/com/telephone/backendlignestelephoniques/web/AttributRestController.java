package com.telephone.backendlignestelephoniques.web;

import com.telephone.backendlignestelephoniques.entities.Attribut;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.services.Attribut.AttributService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{attributId}/{operateur}")
    public void deleteAttribut(@PathVariable Long attributId, @PathVariable String operateur) {
        try {
            attributService.deleteAttribut(attributId, operateur);
        } catch (ElementNotFoundException e) {
        }
    }

    @PutMapping("/update/{operateur}")
    public void updateAttribut(@PathVariable String operateur, @RequestBody Attribut attribut) {
            attributService.updateAttribut(attribut, operateur);
    }

    @GetMapping
    public List<Attribut> listAttributs() {
        return attributService.listAttribut();
    }


}
