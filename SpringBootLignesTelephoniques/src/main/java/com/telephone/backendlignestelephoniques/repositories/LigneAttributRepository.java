package com.telephone.backendlignestelephoniques.repositories;

import com.telephone.backendlignestelephoniques.entities.LigneAttribut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneAttributRepository extends JpaRepository<LigneAttribut, Long>  {

    void deleteLigneAttributByAttributIdAttribut(Long id);
}
