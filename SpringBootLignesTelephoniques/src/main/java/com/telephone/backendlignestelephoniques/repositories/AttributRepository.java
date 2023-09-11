package com.telephone.backendlignestelephoniques.repositories;

import com.telephone.backendlignestelephoniques.entities.Attribut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttributRepository extends JpaRepository<Attribut, Long> {
    boolean existsByNomAttribut(String nomAttribut);
    Optional<Attribut> findByNomAttribut(String nomAttribut);



    @Query("SELECT a FROM Attribut a WHERE " +
            "(LOWER(a.nomAttribut) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(a.type) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(a.valeurAttribut) LIKE LOWER(CONCAT('%', :kw, '%')))")
    Page<Attribut> getAllAttributs(@Param("kw") String keyword, Pageable pageable);
}
