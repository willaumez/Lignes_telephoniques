package com.telephone.backendlignestelephoniques.repositories;

import com.telephone.backendlignestelephoniques.entities.Historiques;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HistoriquesRepository extends JpaRepository<Historiques, Long> {
    List<Historiques> findByDateActionBefore(Date thresholdDate);

    @Query("SELECT h FROM Historiques h ORDER BY h.dateAction DESC")
    List<Historiques> findAllOrderByDateDesc();

}
