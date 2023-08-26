package com.telephone.backendlignestelephoniques.services.LigneTelephonique;

import com.telephone.backendlignestelephoniques.dtos.LigneTelephoniqueDto;
import com.telephone.backendlignestelephoniques.entities.*;
import com.telephone.backendlignestelephoniques.enums.EtatType;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.mappers.LigneMappers;
import com.telephone.backendlignestelephoniques.repositories.*;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
import jakarta.persistence.Column;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class LigneTelephoniqueServiceImpl implements LigneTelephoniqueService {

    private LigneTelephoniqueRepository ligneTelephoniqueRepository;
    private HistoriqueService historiqueService;
    private TypeLigneRepository typeLigneRepository;
    private AttributRepository attributRepository;

    private LigneMappers ligneMappers;


    /*@Override
    @Transactional
    public void saveLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur) throws ElementNotFoundException {
        ligneTelephonique.setCreatedDate(new Date());

        Set<Attribut> attributSet = ligneTelephonique.getTypeLigne().getAttributs();

        TypeLigne typeLigne = typeLigneRepository.findById(ligneTelephonique.getTypeLigne().getIdType())
                .orElseThrow(() -> new ElementNotFoundException("Type ligne introuvable"));
        typeLigne.setAttributs(attributSet);
        ligneTelephonique.setTypeLigne(typeLigne);

        LigneTelephonique savedLigne = ligneTelephoniqueRepository.save(ligneTelephonique);
        System.out.println("----save(ligneTelephonique)-------    "+savedLigne);

        Set<LigneAttribut> ligneAttributs = new HashSet<>();
        for (Attribut attributFromRequest : typeLigne.getAttributs()) {
            LigneAttribut ligneAttribut = new LigneAttribut();
            Attribut optionalAttribut = attributRepository.findById(attributFromRequest.getIdAttribut())
                    .orElseThrow(() -> new ElementNotFoundException("Attribut introuvable"));

            ligneAttribut.setLigneTelephonique(savedLigne);
            ligneAttribut.setAttribut(optionalAttribut);
            ligneAttribut.setValeurAttribut(attributFromRequest.getValeurAttribut());
            ligneAttributs.add(ligneAttribut);
        }
        savedLigne.setLigneAttributs(ligneAttributs);
        ligneTelephoniqueRepository.save(savedLigne);

        historiqueService.saveHistoriques("Ajout [Ligne-Téléphonique]", savedLigne.getNumeroLigne(), operateur);
    }*/


    @Override
    @Transactional
    public void saveLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur) throws ElementNotFoundException {
        ligneTelephonique.setCreatedDate(new Date());

        Set<Attribut> attributSet = ligneTelephonique.getTypeLigne().getAttributs();

        TypeLigne typeLigne = typeLigneRepository.findById(ligneTelephonique.getTypeLigne().getIdType())
                .orElseThrow(() -> new ElementNotFoundException("Type ligne introuvable"));
        //typeLigne.setAttributs(attributSet);
        ligneTelephonique.setTypeLigne(typeLigne);

        LigneTelephonique savedLigne = ligneTelephoniqueRepository.save(ligneTelephonique);
        System.out.println("----save(ligneTelephonique)-------    "+savedLigne);

        Set<LigneAttribut> ligneAttributs = new HashSet<>();
        for (Attribut attributFromRequest : attributSet) {
            LigneAttribut ligneAttribut = new LigneAttribut();
            Attribut optionalAttribut = attributRepository.findById(attributFromRequest.getIdAttribut())
                    .orElseThrow(() -> new ElementNotFoundException("Attribut introuvable"));

            ligneAttribut.setLigneTelephonique(savedLigne);
            ligneAttribut.setAttribut(optionalAttribut);
            ligneAttribut.setValeurAttribut(attributFromRequest.getValeurAttribut());
            ligneAttributs.add(ligneAttribut);
        }
        savedLigne.setLigneAttributs(ligneAttributs);
        ligneTelephoniqueRepository.save(savedLigne);

        historiqueService.saveHistoriques("Ajout [Ligne-Téléphonique]", savedLigne.getNumeroLigne(), operateur);
    }


    /*@Override
    @Transactional
    public void saveLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur) throws ElementNotFoundException {
        ligneTelephonique.setCreatedDate(new Date());

        Optional<TypeLigne> optionalTypeLigne = typeLigneRepository.findById(ligneTelephonique.getTypeLigne().getIdType());
        if (!optionalTypeLigne.isPresent()) {
            throw new ElementNotFoundException("Type introuvable");
        }
        TypeLigne managedTypeLigne = optionalTypeLigne.get();
        ligneTelephonique.setTypeLigne(managedTypeLigne); // Mettre à jour le type de ligne

        Set<Attribut> attributsFromRequest = ligneTelephonique.getTypeLigne().getAttributs();
        Set<LigneAttribut> ligneAttributs = new HashSet<>();
        for (Attribut attributFromRequest : attributsFromRequest) {
            Optional<Attribut> optionalAttribut = attributRepository.findById(attributFromRequest.getIdAttribut());
            if (!optionalAttribut.isPresent()) {
                continue; // ou lancez une exception si nécessaire
            }
            Attribut attribut = optionalAttribut.get();
            LigneAttribut ligneAttribut = new LigneAttribut();
            ligneAttribut.setLigneTelephonique(ligneTelephonique);
            ligneAttribut.setAttribut(attribut);
            ligneAttribut.setValeurAttribut(attributFromRequest.getValeurAttribut());
            ligneAttributs.add(ligneAttribut);
        }

        ligneTelephonique.setLigneAttributs(ligneAttributs);

        LigneTelephonique savedLigne = ligneTelephoniqueRepository.save(ligneTelephonique);
        historiqueService.saveHistoriques("Ajout [Ligne-Téléphonique]", ligneTelephonique.getNumeroLigne(), operateur);
    }*/


    @Override
    public LigneTelephonique getLigneTelephonique(Long ligneId) throws ElementNotFoundException {
        return ligneTelephoniqueRepository.findById(ligneId)
                .orElseThrow(() -> new ElementNotFoundException("Ligne-Téléphonique introuvable"));
    }

    @Override
    public void deleteLigneTelephonique(Long id, String operateur) throws ElementNotFoundException {
        LigneTelephonique ligneTelephonique = ligneTelephoniqueRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Ligne-Téléphonique avec id " + id + " introuvable"));

        ligneTelephoniqueRepository.deleteById(id);
        historiqueService.saveHistoriques("Suppression [Ligne-Téléphonique]", ligneTelephonique.getNumeroLigne(), operateur);
    }

    @Override
    public LigneTelephonique updateLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur) {
        LigneTelephonique existingLigne = ligneTelephoniqueRepository.findById(ligneTelephonique.getIdLigne())
                .orElseThrow(() -> new EntityNotFoundException("Ligne-Téléphonique not found"));
        ligneTelephonique.setCreatedDate(existingLigne.getCreatedDate());
        LigneTelephonique updatedLigne = ligneTelephoniqueRepository.save(ligneTelephonique);
        historiqueService.saveHistoriques("Mise à jour [Ligne-Téléphonique]", updatedLigne.getNumeroLigne(), operateur);
        return updatedLigne;
    }

    @Override
    public List<LigneTelephoniqueDto> listLigneTelephonique() {
        List<LigneTelephonique> ligneTelephoniques = ligneTelephoniqueRepository.findAll();

        // Conversion de la liste d'entités en liste de DTOs
        List<LigneTelephoniqueDto> ligneTelephoniqueDtos = ligneTelephoniques.stream()
                .map(ligneMappers::fromLigneTelephonique)
                .collect(Collectors.toList());

        System.out.println("ligneTelephoniques---  " + ligneTelephoniqueDtos);

        return ligneTelephoniqueDtos;
    }

    @Override
    public List<LigneTelephonique> listLigneTelephoniqueByType(long typeLigneId) {
        Set<LigneTelephonique> lignesTelephoniques = ligneTelephoniqueRepository.findLigneTelephoniqueByTypeId(typeLigneId);
        return new ArrayList<>(lignesTelephoniques);
    }

}
