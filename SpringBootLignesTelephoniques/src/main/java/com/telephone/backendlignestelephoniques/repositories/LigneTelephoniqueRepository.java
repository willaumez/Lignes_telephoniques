package com.telephone.backendlignestelephoniques.repositories;

import com.telephone.backendlignestelephoniques.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface LigneTelephoniqueRepository extends JpaRepository<LigneTelephonique, Long> {
    //@Query("SELECT ligne FROM LigneTelephonique ligne WHERE ligne.typeLigne.idType = :id")
    //Set<LigneTelephonique> findLigneTelephoniqueByTypeId(long id);

    @Query("SELECT ligne FROM LigneTelephonique ligne WHERE ligne.typeLigne.idType = :id")
    List<LigneTelephonique> findByTypeId(long id);

    Set<LigneTelephonique> findLigneTelephoniqueByTypeId(long id);

    List<LigneTelephonique> findByTypeLigneIdType(Long idType);
    //List<LigneTelephonique> findByTypeLigne(TypeLigne typeLigneFromDB);


   /* @Query("SELECT lt FROM LigneTelephonique lt JOIN lt.attributs attributs " +
            "WHERE KEY(attributs) = :attributName AND VALUE(attributs).valeur = :attributValue")
    List<LigneTelephonique> findByAttributValue(@Param("attributName") String attributName, @Param("attributValue") String attributValue);*/

}
