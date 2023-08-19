package com.telephone.backendlignestelephoniques.repositories;

import com.telephone.backendlignestelephoniques.entities.AttributValue;
import com.telephone.backendlignestelephoniques.entities.Forfait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ForfaitRepository extends JpaRepository<Forfait, Long> {
    Forfait findByNomForfait(String nomForfait);

}