package com.telephone.backendlignestelephoniques.web;

import com.telephone.backendlignestelephoniques.entities.Debit;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.services.Debit.DebitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class DebitRestController {

    private DebitService debitService;


    //====================  list Debits  ======================//
    @GetMapping("/listDebits")
    public List<Debit> listDebits() {
        return debitService.listDebits();
    }

    //====================  une restoration  ======================//
    @GetMapping("/debit/{debitId}")
    public Debit getDebit(@PathVariable Long debitId) throws ElementNotFoundException {
        return debitService.getDebit(debitId);
    }

    //====================  save  ======================//
    @PostMapping("/debit/save/{operateur}")
    public Debit saveDebit(@PathVariable String operateur, @RequestBody Debit debit){
        this.debitService.saveDebit(debit, operateur);
        return debit;
    }

    //====================  delete  ======================//
    @DeleteMapping("/debit/delete/{id}/{operateur}")
    public void deleteDebit(@PathVariable Long id, @PathVariable String operateur) throws ElementNotFoundException {
        this.debitService.deleteDebit(id, operateur);
    }

    //====================  update  ======================//
    @PutMapping("/debit/update/{operateur}")
    public Debit updateDebit(@PathVariable String operateur, @RequestBody Debit debit) {
        return debitService.updateDebit(debit, operateur);
    }

}
