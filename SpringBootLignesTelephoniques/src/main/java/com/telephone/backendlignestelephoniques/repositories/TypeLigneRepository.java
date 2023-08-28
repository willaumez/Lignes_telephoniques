package com.telephone.backendlignestelephoniques.repositories;

import com.telephone.backendlignestelephoniques.entities.TypeLigne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeLigneRepository extends JpaRepository<TypeLigne, Long> {
    TypeLigne findByNomType(String nomType);
    boolean existsByNomType(String nomAttribut);

    @Query("SELECT tl FROM TypeLigne tl JOIN tl.attributs a WHERE a.idAttribut = :id")
    List<TypeLigne> findByAttributId(@Param("id") long id);

    Optional<TypeLigne> findByIdType(Long idType);
}
