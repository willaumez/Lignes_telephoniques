package com.telephone.backendlignestelephoniques.services.LigneTelephonique;

import com.telephone.backendlignestelephoniques.dtos.LigneTelephoniqueDto;
import com.telephone.backendlignestelephoniques.entities.*;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.mappers.LigneMappers;
import com.telephone.backendlignestelephoniques.repositories.*;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    /*@Override
    public void updateLigneTelephonique(LigneTelephonique updatedLigne, String operateur) throws ElementNotFoundException {
        LigneTelephonique existingLigne = ligneTelephoniqueRepository.findById(updatedLigne.getIdLigne()).orElseThrow(() -> new ElementNotFoundException("Ligne téléphonique introuvable"));

        existingLigne.setNumeroLigne(updatedLigne.getNumeroLigne());
        existingLigne.setAffectation(updatedLigne.getAffectation());
        existingLigne.setPoste(updatedLigne.getPoste());
        existingLigne.setEtat(updatedLigne.getEtat());
        existingLigne.setDateLivraison(updatedLigne.getDateLivraison());
        existingLigne.setNumeroSerie(updatedLigne.getNumeroSerie());
        existingLigne.setMontant(updatedLigne.getMontant());
        TypeLigne typeLigne = typeLigneRepository.findById(updatedLigne.getTypeLigne().getIdType()).orElseThrow(() -> new ElementNotFoundException("Type ligne introuvable"));
        existingLigne.setTypeLigne(typeLigne);
        Set<Attribut> newAttributs = updatedLigne.getTypeLigne().getAttributs();
        Set<LigneAttribut> ligneAttributs = new HashSet<>();
        for (Attribut attributFromRequest : newAttributs) {
            LigneAttribut ligneAttribut = new LigneAttribut();
            Attribut optionalAttribut = attributRepository.findById(attributFromRequest.getIdAttribut()).orElseThrow(() -> new ElementNotFoundException("Attribut introuvable"));
            ligneAttribut.setLigneTelephonique(existingLigne);
            ligneAttribut.setAttribut(optionalAttribut);
            ligneAttribut.setValeurAttribut(attributFromRequest.getValeurAttribut());
            ligneAttributs.add(ligneAttribut);
        }
        existingLigne.setLigneAttributs(ligneAttributs);

        LigneTelephonique savedLigne = ligneTelephoniqueRepository.save(existingLigne);

        historiqueService.saveHistoriques("Modification [Ligne-Téléphonique]", savedLigne.getNumeroLigne(), operateur);
    }*/

/*@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class LigneTelephoniqueServiceImpl implements LigneTelephoniqueService {

    private LigneTelephoniqueRepository ligneTelephoniqueRepository;
    private HistoriqueService historiqueService;
    private TypeLigneRepository typeLigneRepository;
    private AttributRepository attributRepository;



    @Override
    public void saveLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur) throws ElementNotFoundException {
        ligneTelephonique.setCreatedDate(new Date());

        // Récupérer le TypeLigne associé à partir de l'ID
        TypeLigne typeLigne = typeLigneRepository.findById(ligneTelephonique.getTypeId())
                .orElseThrow(() -> new ElementNotFoundException("Type ligne introuvable"));

        // Définir l'ID du TypeLigne dans LigneTelephonique
        ligneTelephonique.setTypeId(typeLigne.getIdType());

        LigneTelephonique savedLigne = ligneTelephoniqueRepository.save(ligneTelephonique);

        Set<LigneAttribut> ligneAttributs = new HashSet<>();
        for (Attribut attributFromType : typeLigne.getAttributs()) {
            LigneAttribut ligneAttribut = new LigneAttribut();

            Attribut optionalAttribut = attributRepository.findById(attributFromType.getIdAttribut())
                    .orElseThrow(() -> new ElementNotFoundException("Attribut introuvable"));

            ligneAttribut.setLigneId(savedLigne.getIdLigne());  // Utilisez l'ID de la ligne enregistrée
            ligneAttribut.setAttribut(optionalAttribut);

            ligneAttributs.add(ligneAttribut);
        }

        // Associer les attributs à la ligne enregistrée
        savedLigne.setLigneAttributs(ligneAttributs);
        ligneTelephoniqueRepository.save(savedLigne);

        // Enregistrement dans l'historique
        historiqueService.saveHistoriques("Ajout [Ligne-Téléphonique]", savedLigne.getNumeroLigne(), operateur);
    }






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
    public void updateLigneTelephonique(LigneTelephonique updatedLigne, String operateur) throws ElementNotFoundException {
        LigneTelephonique existingLigne = ligneTelephoniqueRepository.findById(updatedLigne.getIdLigne())
                .orElseThrow(() -> new ElementNotFoundException("Ligne téléphonique introuvable"));

        // Mise à jour des propriétés simples
        existingLigne.setNumeroLigne(updatedLigne.getNumeroLigne());
        existingLigne.setAffectation(updatedLigne.getAffectation());
        existingLigne.setPoste(updatedLigne.getPoste());
        existingLigne.setEtat(updatedLigne.getEtat());
        existingLigne.setDateLivraison(updatedLigne.getDateLivraison());
        existingLigne.setNumeroSerie(updatedLigne.getNumeroSerie());
        existingLigne.setMontant(updatedLigne.getMontant());

        // Récupérer le TypeLigne associé à partir de l'ID
        TypeLigne typeLigne = typeLigneRepository.findById(updatedLigne.getTypeId())
                .orElseThrow(() -> new ElementNotFoundException("Type ligne introuvable"));

        // Définir l'ID du TypeLigne dans LigneTelephonique
        existingLigne.setTypeId(typeLigne.getIdType());

        Set<LigneAttribut> ligneAttributs = new HashSet<>();
        for (Attribut attributFromType : typeLigne.getAttributs()) {
            LigneAttribut ligneAttribut = new LigneAttribut();

            Attribut optionalAttribut = attributRepository.findById(attributFromType.getIdAttribut())
                    .orElseThrow(() -> new ElementNotFoundException("Attribut introuvable"));

            ligneAttribut.setLigneId(existingLigne.getIdLigne());  // Utilisez l'ID de la ligne existante
            ligneAttribut.setAttribut(optionalAttribut);

            ligneAttributs.add(ligneAttribut);
        }

        // Associer les attributs à la ligne existante
        existingLigne.setLigneAttributs(ligneAttributs);
        LigneTelephonique savedLigne = ligneTelephoniqueRepository.save(existingLigne);

        // Enregistrement dans l'historique
        historiqueService.saveHistoriques("Modification [Ligne-Téléphonique]", savedLigne.getNumeroLigne(), operateur);
    }




    @Override
    public List<LigneTelephonique> listLigneTelephonique() {
        List<LigneTelephonique> ligneTelephoniques = ligneTelephoniqueRepository.findAll();
        // Conversion de la liste d'entités en liste de DTOs
        //System.out.println("ligneTelephoniques---  " + ligneTelephoniqueDtos);
        return ligneTelephoniques.stream()
                .map(fromLigneTelephonique)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneTelephonique> listLigneTelephoniqueByType(long typeLigneId) {
        Set<LigneTelephonique> lignesTelephoniques = ligneTelephoniqueRepository.findLigneTelephoniqueByTypeId(typeLigneId);
        return new ArrayList<>(lignesTelephoniques);
    }

}*/

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
@AllArgsConstructor
public class LigneTelephoniqueServiceImpl implements LigneTelephoniqueService {

    private LigneTelephoniqueRepository ligneTelephoniqueRepository;
    private HistoriqueService historiqueService;
    private TypeLigneRepository typeLigneRepository;
    private AttributRepository attributRepository;
    private LigneAttributRepository ligneAttributRepository;

    /*@Override
    public void saveLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur) throws EntityNotFoundException {
        System.out.println("Sauvegarde de la ligne téléphonique en cours......  ");
        ligneTelephonique.setCreatedDate(new Date());

        TypeLigne typeLigne = typeLigneRepository.findById(ligneTelephonique.getTypeLigne().getIdType())
                .orElseThrow(() -> new EntityNotFoundException("Type ligne introuvable"));

        ligneTelephonique.setTypeId(typeLigne.getIdType());
        ligneTelephonique.setNomTypeLigne(typeLigne.getNomType());

        LigneTelephonique savedLigne = ligneTelephoniqueRepository.save(ligneTelephonique);
        System.out.println("Ligne sauvegardée: " + savedLigne);

        Set<LigneAttribut> ligneAttributs = new HashSet<>();
        for (Attribut attributFromType : typeLigne.getAttributs()) {
            LigneAttribut ligneAttribut = new LigneAttribut();
            Attribut optionalAttribut = attributRepository.findById(attributFromType.getIdAttribut())
                    .orElseThrow(() -> new EntityNotFoundException("Attribut introuvable"));

            ligneAttribut.setLigneId(savedLigne.getIdLigne());
            ligneAttribut.setAttribut(optionalAttribut);

            ligneAttributs.add(ligneAttribut);
        }


        savedLigne.setLigneAttributs(ligneAttributs);
        ligneTelephoniqueRepository.save(savedLigne);
        System.out.println("Attributs associés à la ligne: " + ligneAttributs);

        historiqueService.saveHistoriques("Ajout [Ligne-Téléphonique]", savedLigne.getNumeroLigne(), operateur);
        System.out.println("Historique sauvegardé pour la nouvelle ligne.");
    }*/

    @Override
    public void saveLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur) throws EntityNotFoundException {
        System.out.println("Sauvegarde de la ligne téléphonique en cours......  ");
        ligneTelephonique.setCreatedDate(new Date());

        TypeLigne typeLigne = typeLigneRepository.findById(ligneTelephonique.getTypeLigne().getIdType())
                .orElseThrow(() -> new EntityNotFoundException("Type ligne introuvable"));

        ligneTelephonique.setTypeId(typeLigne.getIdType());
        //ligneTelephonique.setNomTypeLigne(typeLigne.getNomType());

        LigneTelephonique savedLigne = ligneTelephoniqueRepository.save(ligneTelephonique);
        System.out.println("Ligne sauvegardée: " + savedLigne);

        Set<LigneAttribut> ligneAttributs = new HashSet<>();

        Set<Attribut> attributsFromRequest = ligneTelephonique.getTypeLigne().getAttributs();
        for (Attribut attributFromRequest : attributsFromRequest) {
            Optional<Attribut> optionalAttribut = attributRepository.findById(attributFromRequest.getIdAttribut());
            if (!optionalAttribut.isPresent()) {
                continue; // ou lancez une exception si nécessaire
            }
            Attribut attribut = optionalAttribut.get();
            LigneAttribut ligneAttribut = new LigneAttribut();
            ligneAttribut.setLigneId(savedLigne.getIdLigne());
            ligneAttribut.setAttribut(attribut);
            ligneAttribut.setValeurAttribut(attributFromRequest.getValeurAttribut()); // Insère la valeur de l'attribut de la requête
            ligneAttributs.add(ligneAttribut);
        }

        savedLigne.setLigneAttributs(ligneAttributs);
        ligneTelephoniqueRepository.save(savedLigne);
        System.out.println("Attributs associés à la ligne: " + ligneAttributs);

        historiqueService.saveHistoriques("Ajout [Ligne-Téléphonique]", savedLigne.getNumeroLigne(), operateur);
        System.out.println("Historique sauvegardé pour la nouvelle ligne.");
    }


    @Override
    public LigneTelephonique getLigneTelephonique(Long ligneId) throws EntityNotFoundException {
        System.out.println("Récupération de la ligne téléphonique en cours...");
        return ligneTelephoniqueRepository.findById(ligneId)
                .orElseThrow(() -> new EntityNotFoundException("Ligne-Téléphonique introuvable"));
    }

    @Override
    public void deleteLigneTelephonique(Long id, String operateur) throws EntityNotFoundException {
        System.out.println("Suppression de la ligne téléphonique en cours...");
        LigneTelephonique ligneTelephonique = ligneTelephoniqueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ligne-Téléphonique avec id " + id + " introuvable"));

        // Supprimer les ligne Attributs
        for (LigneAttribut ligneAttribut : ligneTelephonique.getLigneAttributs()) {
            ligneAttributRepository.deleteById(ligneAttribut.getId());
        }

        ligneTelephoniqueRepository.deleteById(id);
        historiqueService.saveHistoriques("Suppression [Ligne-Téléphonique]", ligneTelephonique.getNumeroLigne(), operateur);
        System.out.println("Ligne téléphonique supprimée et historique sauvegardé.");
    }

    @Override
    public void updateLigneTelephonique(LigneTelephonique updatedLigne, String operateur) throws EntityNotFoundException {
        System.out.println("Mise à jour de la ligne téléphonique en cours..." + updatedLigne);

        // Étape 1: Trouver la ligne téléphonique existante par son ID
        LigneTelephonique existingLigne = ligneTelephoniqueRepository.findById(updatedLigne.getIdLigne())
                .orElseThrow(() -> new EntityNotFoundException("Ligne téléphonique introuvable"));

        // Étape 2: Récupérer les LigneAttribut existants par ligneId
        Set<LigneAttribut> existingLigneAttributs = existingLigne.getLigneAttributs();

        // Étape 3: Mettre à jour les valeurs de valeurAttribut
        for (LigneAttribut updatedLigneAttribut : updatedLigne.getLigneAttributs()) {
            for (LigneAttribut existingLigneAttribut : existingLigneAttributs) {
                if (existingLigneAttribut.getId() == updatedLigneAttribut.getId()) {
                    existingLigneAttribut.setValeurAttribut(updatedLigneAttribut.getValeurAttribut());
                }
            }
        }

        // Étape 4: Enregistrez les modifications
        existingLigne.setLigneAttributs(existingLigneAttributs);
        LigneTelephonique savedLigne = ligneTelephoniqueRepository.save(existingLigne);
        System.out.println("Ligne mise à jour: " + savedLigne);

        // Étape 5: Enregistrement dans l'historique
        historiqueService.saveHistoriques("Modification [Ligne-Téléphonique]", savedLigne.getNumeroLigne(), operateur);
        System.out.println("Historique de la mise à jour sauvegardé.");
    }



    @Override
    public List<LigneTelephonique> listLigneTelephonique() {
        System.out.println("Récupération de la liste des lignes téléphoniques en cours...");
        return ligneTelephoniqueRepository.findAll();
    }

    @Override
    public List<LigneTelephonique> listLigneTelephoniqueByType(long typeLigneId) {
        System.out.println("Récupération des lignes téléphoniques par type en cours...");
        Set<LigneTelephonique> lignesTelephoniques = ligneTelephoniqueRepository.findLigneTelephoniqueByTypeId(typeLigneId);
        return new ArrayList<>(lignesTelephoniques);
    }
}

