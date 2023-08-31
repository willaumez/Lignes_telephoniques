package com.telephone.backendlignestelephoniques.repositories;

import com.telephone.backendlignestelephoniques.entities.Corbeille;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CorbeilleRepository extends JpaRepository<Corbeille, Long> {
    List<Corbeille> findByDateSuppressionBefore(Date thresholdDate);

}
