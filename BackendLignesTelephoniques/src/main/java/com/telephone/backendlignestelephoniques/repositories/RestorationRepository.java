package com.telephone.backendlignestelephoniques.repositories;

import com.telephone.backendlignestelephoniques.entities.Restoration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RestorationRepository extends JpaRepository<Restoration, Long> {

    List<Restoration> findByDateSuppressionBefore(Date thresholdDate);

}
