package com.telephone.backendlignestelephoniques.services.Debit;

import com.telephone.backendlignestelephoniques.entities.Debit;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class DebitServiceImpl implements DebitService {
    @Override
    public void saveDebit(Debit debit) {
    }

    @Override
    public Debit getDebit(Long debitId) throws ElementNotFoundException {
        return null;
    }

    @Override
    public void deleteDebit(Long id) {

    }

    @Override
    public Debit updateDebit(Debit debit) {
        return null;
    }

    @Override
    public List<Debit> listDebits() {
        return null;
    }
}
