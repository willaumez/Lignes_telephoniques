package com.telephone.backendlignestelephoniques.services.LigneTelephonique;

import com.telephone.backendlignestelephoniques.embeddable.AttributValeur;
import com.telephone.backendlignestelephoniques.entities.*;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.*;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private CorbeilleRepository corbeilleRepository;

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
        historiqueService.saveHistoriques("Ajout [Ligne-Téléphonique]", savedLigne.getNumeroLigne(), operateur);
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
        saveInCorbeille(ligneTelephonique);
        historiqueService.saveHistoriques("Suppression [Ligne-Téléphonique]", ligneTelephonique.getNumeroLigne(), operateur);
    }

    @Override
    public void updateLigneTelephonique(LigneTelephonique updatedLigne, String operateur) throws EntityNotFoundException {
        System.out.println("\n\n\n\nMise à jour de la ligne téléphonique en cours..." + updatedLigne+"\n\n\n\n");

        // Étape 1: Trouver la ligne téléphonique existante par son ID
        LigneTelephonique existingLigne = ligneTelephoniqueRepository.findById(updatedLigne.getIdLigne())
                .orElseThrow(() -> new EntityNotFoundException("Ligne téléphonique introuvable"));

        System.out.println("\n\n\n\nexistingLigne       ..." + existingLigne+"\n\n\n\n");

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

   /* @Override
    public List<LigneTelephonique> listLigneTelephonique() {
        System.out.println("Récupération de la liste des lignes téléphoniques en cours...");
        return ligneTelephoniqueRepository.findAll();
    }*/

    @Override
    public Page<LigneTelephonique> listLigneTelephonique(int page, int size, String kw) {
        Pageable pageable = PageRequest.of(page, size);
        return ligneTelephoniqueRepository.getAllLignesTelephoniques(kw, pageable);
        //return ligneTelephoniqueRepository.findAll();
    }

    @Override
    public Page<LigneTelephonique> listLigneTelephoniqueByType(int page, int size, String kw, Long typeId) {
        Pageable pageable = PageRequest.of(page, size);
        return ligneTelephoniqueRepository.getAllLignesTelephoniquesByType(kw, typeId, pageable);
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








    //Corbeille
    @Override
    public void saveInCorbeille(LigneTelephonique ligneTelephonique) {
        Corbeille corbeille = new Corbeille();
        corbeille.setDateSuppression(new Date());
        corbeille.setNumeroLigne(ligneTelephonique.getNumeroLigne());
        corbeille.setAffectation(ligneTelephonique.getAffectation());
        corbeille.setPoste(ligneTelephonique.getPoste());
        corbeille.setEtat(ligneTelephonique.getEtat());
        corbeille.setDateLivraison(ligneTelephonique.getDateLivraison());
        corbeille.setNumeroSerie(ligneTelephonique.getNumeroSerie());
        corbeille.setMontant(ligneTelephonique.getMontant());
        corbeille.setCreatedDate(ligneTelephonique.getCreatedDate());
        corbeille.setTypeId(ligneTelephonique.getTypeId());
        corbeille.setNomType(ligneTelephonique.getTypeLigne().getNomType());
        corbeille.setDescriptionType(ligneTelephonique.getTypeLigne().getDescriptionType());
        //corbeille.setLigneAttributs(ligneMappers.fromLigneAttribut(ligneTelephonique.getLigneAttributs()));

        Set<AttributValeur> attributValeurs = new HashSet<>();
        for (LigneAttribut ligneAttribut : ligneTelephonique.getLigneAttributs()) {
            AttributValeur attributValeur = new AttributValeur();
            attributValeur.setNomAttribut(ligneAttribut.getAttribut().getNomAttribut());
            attributValeur.setValeurAttribut(ligneAttribut.getValeurAttribut());
            // Prendre la liste des énumérations de l'attribut
            List<String> enumerations = ligneAttribut.getAttribut().getEnumeration();
            String concatenatedEnumerations = String.join("||", enumerations);
            attributValeur.setEnumeration(concatenatedEnumerations);
            attributValeurs.add(attributValeur);
        }

        corbeille.setAttributValeurs(attributValeurs);

        corbeilleRepository.save(corbeille);
    }

    @Override
    public Corbeille getElementCorbeille(Long restorationId) throws ElementNotFoundException {
        return corbeilleRepository.findById(restorationId)
                .orElseThrow(() -> new ElementNotFoundException("----- Élément non trouvé -----"));
    }

    @Override
    public void deleteFromCorbeille(Long id, String operateur) throws ElementNotFoundException {
        Corbeille corbeille = corbeilleRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("----- Élément non trouvé -----"));
        corbeilleRepository.deleteById(id);
        historiqueService.saveHistoriques("Suppression d'une ligne de la corbeille", corbeille.getNumeroLigne(), operateur);
    }

    @Override
    public void restorationOfElement(Long id, String operateur) throws ElementNotFoundException {
        Corbeille corbeille = corbeilleRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Element avec l'id " + id + " non trouvé"));

        //Créer la ligne
        LigneTelephonique ligneTelephonique = new LigneTelephonique();
        ligneTelephonique.setNumeroLigne(corbeille.getNumeroLigne());
        ligneTelephonique.setAffectation(corbeille.getAffectation());
        ligneTelephonique.setPoste(corbeille.getPoste());
        ligneTelephonique.setEtat(corbeille.getEtat());
        ligneTelephonique.setDateLivraison(corbeille.getDateLivraison());
        ligneTelephonique.setNumeroSerie(corbeille.getNumeroSerie());
        ligneTelephonique.setMontant(corbeille.getMontant());
        ligneTelephonique.setCreatedDate(new Date());

        // Trouver ou créer le type de ligne
        TypeLigne typeLigne = typeLigneRepository.findById(corbeille.getTypeId()).orElse(null);
        if (typeLigne == null) {
            typeLigne = typeLigneRepository.findByNomType(corbeille.getNomType()).orElse(null);
        }
        if (typeLigne == null) {
            // Si le type de ligne n'existe pas, le créer
            typeLigne = new TypeLigne();
            typeLigne.setNomType(corbeille.getNomType());
            typeLigne.setDescriptionType(corbeille.getDescriptionType());
            typeLigneRepository.save(typeLigne);
        }
        ligneTelephonique.setTypeLigne(typeLigne);

        // Restaurer les attributs
        Set<LigneAttribut> ligneAttributsToRestore = new HashSet<>();
        // Parcourir tous les attributs de la corbeille et les restaurer
        for (AttributValeur attributValeur : corbeille.getAttributValeurs()) {
            // Créer et initialiser le LigneAttribut à partir de AttributValeur
            LigneAttribut ligneAttribut = new LigneAttribut();

            // Vérifier si l'attribut existe déjà dans la base de données
            Attribut existingAttribut = attributRepository.findByNomAttribut(attributValeur.getNomAttribut()).orElse(null);
            if(existingAttribut == null) {
                // Si l'attribut n'existe pas, le créer
                Attribut newAttribut = new Attribut();
                newAttribut.setNomAttribut(attributValeur.getNomAttribut());

                // Obtenir la chaîne concaténée de attributValeur
                String concatenatedEnumerations = attributValeur.getEnumeration();
                List<String> enumerationList = new ArrayList<>();
                if (concatenatedEnumerations != null && !concatenatedEnumerations.isEmpty()) {
                    String[] enumerationArray = concatenatedEnumerations.split("\\|\\|");
                    enumerationList = new ArrayList<>(Arrays.asList(enumerationArray));
                }
                newAttribut.setEnumeration(enumerationList);
                // (initialisez les autres champs de newAttribut si nécessaire)
                attributRepository.save(newAttribut);
                ligneAttribut.setAttribut(newAttribut);
            } else {
                ligneAttribut.setAttribut(existingAttribut);
            }

            ligneAttribut.setValeurAttribut(attributValeur.getValeurAttribut());

            // Ajouter à l'ensemble des attributs à restaurer
            ligneAttributsToRestore.add(ligneAttribut);
        }

        ligneTelephonique.setLigneAttributs(ligneAttributsToRestore);

        // Sauvegarder la ligne restaurée
        saveLigneTelephonique(ligneTelephonique, operateur);

        // Supprimer l'entrée de la corbeille
        corbeilleRepository.deleteById(id);

        // Ajouter un historique
        historiqueService.saveHistoriques("Restauration d'une ligne depuis la corbeille", ligneTelephonique.getNumeroLigne(), operateur);
    }
    @Override
    public List<Corbeille> listCorbeille() {
        return corbeilleRepository.findAll();
    }

   /* @Override
    public Page<Corbeille> listCorbeillePage(int page, int size, String kw) {
        Pageable pageable = PageRequest.of(page, size);
        return corbeilleRepository.getAllElementPage(kw, pageable);
    }*/

    @Override
    public Page<Corbeille> listCorbeillePage(int page, int size, String kw) {
        Pageable pageable = PageRequest.of(page, size);
        return corbeilleRepository.getAllElementPage(kw, pageable);
    }

    @Override
    public void deleteAllCorbeille(String operateur) {
        corbeilleRepository.deleteAll();
        historiqueService.saveHistoriques("Suppression totale des points de restauration", "Toute la corbeille", operateur);
    }

    @Override
    public Map<String, Integer> restorationAllElement(String operateur) {
        int restoredCount = 0;
        int notRestoredCount = 0;

        List<Corbeille> allElementsInCorbeille = corbeilleRepository.findAll();

        for (Corbeille corbeille : allElementsInCorbeille) {
            try {
                restorationOfElement(corbeille.getIdCorbeille(), operateur);
                restoredCount++;
            } catch (ElementNotFoundException e) {
                System.err.println("Erreur lors de la restauration de l'élément avec l'ID " + corbeille.getIdCorbeille() + ": " + e.getMessage());
                notRestoredCount++;
            }
        }

        Map<String, Integer> result = new HashMap<>();
        result.put("restoredCount", restoredCount);
        result.put("notRestoredCount", notRestoredCount);
        return result;
    }






}

