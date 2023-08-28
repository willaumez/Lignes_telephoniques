package com.telephone.backendlignestelephoniques.services.LigneTelephonique;

import com.telephone.backendlignestelephoniques.entities.*;
import com.telephone.backendlignestelephoniques.repositories.*;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class LigneTelephoniqueServiceImpl implements LigneTelephoniqueService {

    private LigneTelephoniqueRepository ligneTelephoniqueRepository;
    private HistoriqueService historiqueService;
    private TypeLigneRepository typeLigneRepository;
    private AttributRepository attributRepository;
    private LigneAttributRepository ligneAttributRepository;

    @Override
    public void saveLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur) throws EntityNotFoundException {
        if (ligneTelephoniqueRepository.existsByNumeroLigne(ligneTelephonique.getNumeroLigne())) {
            throw new IllegalArgumentException("Une ligne Téléphonique du même numéro existe déjà.");
        }
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
        //charger les donneés
        existingLigne.setNumeroLigne(updatedLigne.getNumeroLigne());
        existingLigne.setAffectation(updatedLigne.getAffectation());
        existingLigne.setPoste(updatedLigne.getPoste());
        existingLigne.setEtat(updatedLigne.getEtat());
        existingLigne.setDateLivraison(updatedLigne.getDateLivraison());
        existingLigne.setMontant(updatedLigne.getMontant());
        existingLigne.setNumeroSerie(updatedLigne.getNumeroSerie());

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


    //Rapprochement
    @Override
    public List<Rapprochement> rapprochementList() {
        List<LigneTelephonique> ligneTelephoniques = ligneTelephoniqueRepository.findAll();
        return ligneTelephoniques.stream().map(ligneTelephonique -> {
            Rapprochement rapprochement = new Rapprochement();
            rapprochement.setNumero(ligneTelephonique.getNumeroLigne());
            rapprochement.setMontant(ligneTelephonique.getMontant());
            return rapprochement;
        }).collect(Collectors.toList());
    }


}

