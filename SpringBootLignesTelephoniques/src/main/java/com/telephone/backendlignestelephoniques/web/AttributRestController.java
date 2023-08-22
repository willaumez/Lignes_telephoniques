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

    @PostMapping
    public ResponseEntity<String> saveAttribut(@RequestBody Attribut attribut, @RequestParam String operateur) {
        attributService.saveAttribut(attribut, operateur);
        return ResponseEntity.ok("Attribut saved successfully");
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

    @DeleteMapping("/{attributId}")
    public ResponseEntity<String> deleteAttribut(@PathVariable Long attributId, @RequestParam String operateur) {
        try {
            attributService.deleteAttribut(attributId, operateur);
            return ResponseEntity.ok("Attribut deleted successfully");
        } catch (ElementNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{attributId}")
    public ResponseEntity<String> updateAttribut(@PathVariable Long attributId, @RequestBody Attribut attribut,
                                                 @RequestParam String operateur) {
        try {
            attribut.setIdAttribut(attributId); // Assurez-vous que l'ID est correctement défini
            attributService.updateAttribut(attribut, operateur);
            return ResponseEntity.ok("Attribut updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Attribut>> listAttributs() {
        List<Attribut> attributs = attributService.listAttribut();
        return ResponseEntity.ok(attributs);
    }


}
