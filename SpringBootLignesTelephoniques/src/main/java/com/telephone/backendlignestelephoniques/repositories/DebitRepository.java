package com.telephone.backendlignestelephoniques.repositories;

import com.telephone.backendlignestelephoniques.entities.Debit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebitRepository extends JpaRepository<Debit, Long> {
    Debit findByNomDebit(String nomAttribut);
}
