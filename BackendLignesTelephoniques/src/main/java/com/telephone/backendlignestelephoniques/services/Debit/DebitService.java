package com.telephone.backendlignestelephoniques.services.Debit;

import com.telephone.backendlignestelephoniques.entities.Debit;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;

import java.util.List;

public interface DebitService {

    //=============================================  Debit  ========================================================//
    void saveDebit(Debit debit);
    Debit getDebit(Long debitId) throws ElementNotFoundException;
    void deleteDebit(Long id);
    Debit updateDebit(Debit debit);
    List<Debit> listDebits();

}
