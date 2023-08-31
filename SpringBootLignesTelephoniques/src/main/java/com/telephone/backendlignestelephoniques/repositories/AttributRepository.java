package com.telephone.backendlignestelephoniques.repositories;

import com.telephone.backendlignestelephoniques.entities.Attribut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttributRepository extends JpaRepository<Attribut, Long> {
    boolean existsByNomAttribut(String nomAttribut);
    Optional<Attribut> findByNomAttribut(String nomAttribut);



}
