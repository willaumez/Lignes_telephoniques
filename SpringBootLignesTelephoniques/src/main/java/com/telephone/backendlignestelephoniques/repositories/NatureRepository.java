package com.telephone.backendlignestelephoniques.repositories;

import com.telephone.backendlignestelephoniques.entities.NatureLigne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NatureRepository extends JpaRepository<NatureLigne, Long> {
    NatureLigne findByNomNature(String nomAttribut);
}