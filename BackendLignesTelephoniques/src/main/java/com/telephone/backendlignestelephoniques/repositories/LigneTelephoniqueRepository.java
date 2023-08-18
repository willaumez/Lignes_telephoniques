package com.telephone.backendlignestelephoniques.repositories;

import com.telephone.backendlignestelephoniques.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneTelephoniqueRepository extends JpaRepository<LigneTelephonique, Long> {
    List<LigneTelephonique> findByDebit(Debit debit);

    List<LigneTelephonique> findByDirection(Direction direction);

    List<LigneTelephonique> findByFonction(Fonction fonction);

    List<LigneTelephonique> findByForfait(Forfait forfait);

    List<LigneTelephonique> findByNatureLigne(NatureLigne natureLigne);

    List<LigneTelephonique> findByTypeLigne(TypeLigne typeLigne);


}
