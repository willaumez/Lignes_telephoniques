package com.telephone.backendlignestelephoniques.repositories;

import com.telephone.backendlignestelephoniques.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    boolean existsByNumeroLigne(String numeroLigne);

    //List<LigneTelephonique> findByTypeLigne(TypeLigne typeLigneFromDB);


   /* @Query("SELECT lt FROM LigneTelephonique lt JOIN lt.attributs attributs " +
            "WHERE KEY(attributs) = :attributName AND VALUE(attributs).valeur = :attributValue")
    List<LigneTelephonique> findByAttributValue(@Param("attributName") String attributName, @Param("attributValue") String attributValue);*/


    @Query("SELECT DISTINCT l FROM LigneTelephonique l LEFT JOIN l.ligneAttributs la WHERE " +
            "(LOWER(l.numeroLigne) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(l.affectation) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(l.poste) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(l.numeroSerie) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(l.typeLigne.nomType) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(:kw IS NULL OR STR(l.createdDate) LIKE CONCAT('%', :kw, '%')) OR " +
            "(la.valeurAttribut LIKE :kw)")
    Page<LigneTelephonique> getAllLignesTelephoniques(@Param("kw") String keyword, Pageable pageable);


    @Query("SELECT DISTINCT l FROM LigneTelephonique l LEFT JOIN l.ligneAttributs la WHERE " +
            "(l.typeId = :idType) AND (" +
            "(LOWER(l.numeroLigne) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(l.affectation) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(l.poste) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(l.numeroSerie) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(l.typeLigne.nomType) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(:kw IS NULL OR STR(l.createdDate) LIKE CONCAT('%', :kw, '%')) OR " +
            "(la.valeurAttribut LIKE :kw))")
    Page<LigneTelephonique> getAllLignesTelephoniquesByType(@Param("kw") String keyword, @Param("idType") Long typeId, Pageable pageable);




}
