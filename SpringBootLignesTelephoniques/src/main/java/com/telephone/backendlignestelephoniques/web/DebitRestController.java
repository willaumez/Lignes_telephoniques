package com.telephone.backendlignestelephoniques.web;

import com.telephone.backendlignestelephoniques.entities.Debit;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.services.Debit.DebitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
@Tag(name = "DebitRestController")
public class DebitRestController {

    private DebitService debitService;

    @Operation(summary = "Récupérer la liste des débits")
    @GetMapping("/listDebits")
    public List<Debit> listDebits() {
        return debitService.listDebits();
    }

    @Operation(summary = "Récupérer un débit par ID")
    @GetMapping("/debit/{debitId}")
    public Debit getDebit(@PathVariable Long debitId) throws ElementNotFoundException {
        return debitService.getDebit(debitId);
    }

    @Operation(summary = "Enregistrer un nouveau débit")
    @PostMapping("/debit/save/{operateur}")
    public Debit saveDebit(@PathVariable String operateur, @RequestBody Debit debit) {
        this.debitService.saveDebit(debit, operateur);
        return debit;
    }

    @Operation(summary = "Supprimer un débit par ID et opérateur")
    @DeleteMapping("/debit/delete/{id}/{operateur}")
    public void deleteDebit(@PathVariable Long id, @PathVariable String operateur) throws ElementNotFoundException {
        this.debitService.deleteDebit(id, operateur);
    }

    @Operation(summary = "Mettre à jour un débit")
    @PutMapping("/debit/update/{operateur}")
    public Debit updateDebit(@PathVariable String operateur, @RequestBody Debit debit) {
        return debitService.updateDebit(debit, operateur);
    }
}
