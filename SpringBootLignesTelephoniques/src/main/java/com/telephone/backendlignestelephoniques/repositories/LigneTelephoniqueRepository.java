package com.telephone.backendlignestelephoniques.repositories;

import com.telephone.backendlignestelephoniques.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneTelephoniqueRepository extends JpaRepository<LigneTelephonique, Long> {

    /*List<LigneTelephonique> findByDebit(Debit debit);

    List<LigneTelephonique> findByFonction(Fonction fonction);

    List<LigneTelephonique> findByForfait(Forfait forfait);

    List<LigneTelephonique> findByNatureLigne(NatureLigne natureLigne);

    List<LigneTelephonique> findByTypeLigne(TypeLigne typeLigne);
    List<LigneTelephonique> findByDirection(Direction direction);*/

    @Query("SELECT lt FROM LigneTelephonique lt JOIN lt.attributs attributs " +
            "WHERE KEY(attributs) = :attributName AND VALUE(attributs).valeur = :attributValue")
    List<LigneTelephonique> findByAttributValue(@Param("attributName") String attributName, @Param("attributValue") String attributValue);

}
