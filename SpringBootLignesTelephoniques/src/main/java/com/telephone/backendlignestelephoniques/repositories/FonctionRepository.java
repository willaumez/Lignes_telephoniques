package com.telephone.backendlignestelephoniques.repositories;

import com.telephone.backendlignestelephoniques.entities.Fonction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FonctionRepository extends JpaRepository<Fonction, Long> {
    Fonction findByNomFonction(String nomAttribut);

}
