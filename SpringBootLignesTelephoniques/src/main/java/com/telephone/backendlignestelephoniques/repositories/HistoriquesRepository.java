package com.telephone.backendlignestelephoniques.repositories;

import com.telephone.backendlignestelephoniques.entities.Historiques;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HistoriquesRepository extends JpaRepository<Historiques, Long> {
    List<Historiques> findByDateActionBefore(Date thresholdDate);

    @Query("SELECT h FROM Historiques h ORDER BY h.dateAction DESC")
    List<Historiques> findAllOrderByDateDesc();

    @Query("SELECT h FROM Historiques h WHERE " +
            "(LOWER(h.actionEffectue) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(h.elementCible) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(h.nomOperateur) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(:kw IS NULL OR STR(h.dateAction) LIKE CONCAT('%', :kw, '%'))")
    Page<Historiques> getAllHistoriques(@Param("kw") String keyword, Pageable pageable);

}
