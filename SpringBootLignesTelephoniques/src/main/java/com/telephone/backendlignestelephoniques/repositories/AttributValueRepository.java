package com.telephone.backendlignestelephoniques.repositories;

import com.telephone.backendlignestelephoniques.entities.AttributValue;
import com.telephone.backendlignestelephoniques.entities.LigneTelephonique;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributValueRepository extends JpaRepository<AttributValue, Long> {
    void deleteByLigneTelephonique(LigneTelephonique ligneTelephonique);

}
