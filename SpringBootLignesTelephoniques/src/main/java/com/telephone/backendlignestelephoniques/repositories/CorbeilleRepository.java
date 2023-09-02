package com.telephone.backendlignestelephoniques.repositories;

import com.telephone.backendlignestelephoniques.entities.Corbeille;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CorbeilleRepository extends JpaRepository<Corbeille, Long> {
    List<Corbeille> findByDateSuppressionBefore(Date thresholdDate);



    //Page<Corbeille> getAllElementPage(@Param("kw") String keyword, Pageable pageable);


/*    @Query("SELECT c FROM Corbeille c WHERE " +
            "(LOWER(c.numeroLigne) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(c.affectation) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(c.poste) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(c.numeroSerie) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(CAST(c.montant AS string)) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(c.nomType) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(FUNCTION('DATE_FORMAT', c.dateSuppression, '%Y-%m-%d %T') LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(FUNCTION('DATE_FORMAT', c.dateLivraison, '%Y-%m-%d %T') LIKE LOWER(CONCAT('%', :kw, '%')))")
    Page<Corbeille> getAllElementPage(@Param("kw") String keyword, Pageable pageable);*/

    @Query("SELECT c FROM Corbeille c WHERE " +
            "(LOWER(c.numeroLigne) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(c.affectation) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(c.poste) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(c.numeroSerie) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(CAST(c.montant AS string)) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(c.nomType) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(FUNCTION('DATE_FORMAT', c.dateSuppression, '%Y-%m-%d %T') LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(FUNCTION('DATE_FORMAT', c.dateLivraison, '%Y-%m-%d %T') LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(c.etat) LIKE LOWER(CONCAT('%', :kw, '%')))")
    Page<Corbeille> getAllElementPage(@Param("kw") String keyword, Pageable pageable);








}
