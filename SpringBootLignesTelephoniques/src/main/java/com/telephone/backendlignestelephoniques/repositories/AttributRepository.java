package com.telephone.backendlignestelephoniques.repositories;

import com.telephone.backendlignestelephoniques.entities.Attribut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributRepository extends JpaRepository<Attribut, Long> {
    boolean existsByNomAttribut(String nomAttribut);
    Attribut findByNomAttribut(String nomAttribut);

}
