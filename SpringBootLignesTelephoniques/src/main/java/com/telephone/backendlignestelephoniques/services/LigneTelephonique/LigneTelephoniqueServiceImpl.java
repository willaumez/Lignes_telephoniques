package com.telephone.backendlignestelephoniques.services.LigneTelephonique;

import com.telephone.backendlignestelephoniques.entities.*;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.*;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class LigneTelephoniqueServiceImpl implements LigneTelephoniqueService {

    private LigneTelephoniqueRepository ligneTelephoniqueRepository;
    private HistoriqueService historiqueService;
    private TypeLigneRepository typeLigneRepository;

    private ForfaitRepository forfaitRepository;
    private AttributValueRepository attributValueRepository;
    private FonctionRepository fonctionRepository;
    private DebitRepository debitRepository;
    private NatureRepository natureRepository;

    /*@Override
    public void saveLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur) {
        ligneTelephonique.setCreatedDate(new Date());
        ligneTelephoniqueRepository.save(ligneTelephonique);
        historiqueService.saveHistoriques("Ajout [Ligne-Téléphonique]", ligneTelephonique.getNumeroLigne(), operateur);
    }*/

    private void createAttributValue(String nomAttribut, Long referenceId, LigneTelephonique ligneTelephonique) {
        AttributValue attributValue = new AttributValue();
        attributValue.setNomAttribut(nomAttribut);
        attributValue.setReferenceId(referenceId);
        attributValue.setLigneTelephonique(ligneTelephonique);
        attributValueRepository.save(attributValue);
    }

    @Override
    public void saveLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur) {
        ligneTelephonique.setCreatedDate(new Date());

        TypeLigne typeLigne = typeLigneRepository.findByNomType(ligneTelephonique.getTypeLigne().getNomType());
        ligneTelephonique.setTypeLigne(typeLigne);

        for (AttributLigne attributligne : typeLigne.getAttributs()) {
            String getAttribut = attributligne.getNomAttribut();
            String typeAttribut = attributligne.getType();

            if ("reference".equals(typeAttribut)) {
                String referenceEntity = attributligne.getReferenceEntity();
                String nomAttribut = ligneTelephonique.getAttributs().get(getAttribut).getValeur();

                switch (referenceEntity) {
                    case "Forfait" -> {
                        Forfait forfait = forfaitRepository.findByNomForfait(nomAttribut);
                        if (forfait != null) {
                            createAttributValue(getAttribut, forfait.getIdForfait(), ligneTelephonique);
                        }
                    }
                    case "Fonction" -> {
                        Fonction fonction = fonctionRepository.findByNomFonction(nomAttribut);
                        if (fonction != null) {
                            createAttributValue(getAttribut, fonction.getIdFonction(), ligneTelephonique);
                        }
                    }
                    case "Debit" -> {
                        Debit debit = debitRepository.findByNomDebit(nomAttribut);
                        if (debit != null) {
                            createAttributValue(getAttribut, debit.getIdDebit(), ligneTelephonique);
                        }
                    }
                    case "NatureLigne" -> {
                        NatureLigne natureLigne = natureRepository.findByNomNature(nomAttribut);
                        if (natureLigne != null) {
                            createAttributValue(getAttribut, natureLigne.getIdNature(), ligneTelephonique);
                        }
                    }
                    default -> {
                        AttributValue attributValue = ligneTelephonique.getAttributs().get(getAttribut);
                        if (attributValue != null && attributValue.getReferenceId() == null) {
                            String valeurAttribut = attributValue.getValeur();

                            // Créer l'AttributValue en tant que valeur simple
                            AttributValue newAttributValue = new AttributValue();
                            newAttributValue.setNomAttribut(getAttribut);
                            newAttributValue.setValeur(valeurAttribut);
                            newAttributValue.setLigneTelephonique(ligneTelephonique);
                            attributValueRepository.save(newAttributValue);
                        }
                    }
                }
            }
        }

        ligneTelephoniqueRepository.save(ligneTelephonique);
        historiqueService.saveHistoriques("Ajout [Ligne-Téléphonique]", ligneTelephonique.getNumeroLigne(), operateur);
    }


    /*public void saveLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur) {
        ligneTelephonique.setCreatedDate(new Date());

        // Récupérer le TypeLigne associé au nom du type de la ligne téléphonique
        TypeLigne typeLigne = typeLigneRepository.findByNomType(ligneTelephonique.getTypeLigne().getNomType());
        ligneTelephonique.setTypeLigne(typeLigne);
        // Enregistrer les attributs spécifiques
        for (AttributLigne attributligne : typeLigne.getAttributs()) {
            String getAttribut = attributligne.getNomAttribut(); // Ajoutez cette ligne pour définir la variable nomAttribut
            String typeAttribut = attributligne.getType();

            // Si l'attribut est une référence
            if ("reference".equals(typeAttribut)) {
                String referenceEntity = attributligne.getReferenceEntity();

                String nomAttribut = ligneTelephonique.getAttributs().get(getAttribut).getValeur();

                // Récupérer la référence à l'entité
                if ("Forfait".equals(referenceEntity)) {
                    Forfait forfait = forfaitRepository.findByNomForfait(nomAttribut);
                    if (forfait != null) {
                        // Créer l'AttributValue en tant que référence vers Forfait
                        AttributValue attributValue = new AttributValue();
                        attributValue.setNomAttribut(nomAttribut);
                        attributValue.setReferenceId(forfait.getIdForfait()); // Stocker l'ID du forfait
                        attributValue.setLigneTelephonique(ligneTelephonique);
                        attributValueRepository.save(attributValue);
                    }
                } else if ("Fonction".equals(referenceEntity)) {
                    Fonction fonction = fonctionRepository.findByNomFonction(nomAttribut);
                    if (fonction != null) {
                        // Créer l'AttributValue en tant que référence vers Fonction
                        AttributValue attributValue = new AttributValue();
                        attributValue.setNomAttribut(nomAttribut);
                        attributValue.setReferenceId(fonction.getIdFonction()); // Stocker l'ID de la fonction
                        attributValue.setLigneTelephonique(ligneTelephonique);
                        attributValueRepository.save(attributValue);
                    }
                } else if ("Debit".equals(referenceEntity)) {
                    Debit debit = debitRepository.findByNomDebit(nomAttribut);
                    if (debit != null) {
                        // Créer l'AttributValue en tant que référence vers Debit
                        AttributValue attributValue = new AttributValue();
                        attributValue.setNomAttribut(nomAttribut);
                        attributValue.setReferenceId(debit.getIdDebit()); // Stocker l'ID du débit
                        attributValue.setLigneTelephonique(ligneTelephonique);
                        attributValueRepository.save(attributValue);
                    }
                } else if ("NatureLigne".equals(referenceEntity)) {
                    NatureLigne natureLigne = natureRepository.findByNomNature(nomAttribut);
                    if (natureLigne != null) {
                        // Créer l'AttributValue en tant que référence vers NatureLigne
                        AttributValue attributValue = new AttributValue();
                        attributValue.setNomAttribut(nomAttribut);
                        attributValue.setReferenceId(natureLigne.getIdNature()); // Stocker l'ID de la nature de ligne
                        attributValue.setLigneTelephonique(ligneTelephonique);
                        attributValueRepository.save(attributValue);
                    }
                }

            }
        }


        ligneTelephoniqueRepository.save(ligneTelephonique);
        historiqueService.saveHistoriques("Ajout [Ligne-Téléphonique]", ligneTelephonique.getNumeroLigne(), operateur);
    }*/

    @Override
    public LigneTelephonique getLigneTelephonique(Long ligneId) throws ElementNotFoundException {
        return ligneTelephoniqueRepository.findById(ligneId)
                .orElseThrow(() -> new ElementNotFoundException("----- LigneTelephonique non trouvé -----"));
    }

    @Override
    public void deleteLigneTelephonique(Long id, String operateur) throws ElementNotFoundException {
        LigneTelephonique ligneTelephonique = ligneTelephoniqueRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Ligne-Telephonique with id " + id + " not found"));

        // Supprimer les attributs associés à cette ligne téléphonique
        attributValueRepository.deleteByLigneTelephonique(ligneTelephonique);

        ligneTelephoniqueRepository.deleteById(id);
        historiqueService.saveHistoriques("Suppression [Ligne-Téléphonique]", ligneTelephonique.getNumeroLigne(), operateur);
    }


    @Override
    public LigneTelephonique updateLigneTelephonique(LigneTelephonique ligneTelephonique, String operateur) {
        LigneTelephonique existingLigne = ligneTelephoniqueRepository.findById(ligneTelephonique.getIdLigne())
                .orElseThrow(() -> new EntityNotFoundException("LigneTelephonique not found"));
        ligneTelephonique.setCreatedDate(existingLigne.getCreatedDate());
        LigneTelephonique updatedLigne = ligneTelephoniqueRepository.save(ligneTelephonique);
        historiqueService.saveHistoriques("Mise à jour [Ligne-Téléphonique]", updatedLigne.getNumeroLigne(), operateur);
        return updatedLigne;
    }

    @Override
    public List<LigneTelephonique> listLigneTelephonique() {
        return ligneTelephoniqueRepository.findAll();
    }

    // find By
    /*@Override
    public List<LigneTelephonique> ligneByDebit(Debit debit) {
        return ligneTelephoniqueRepository.findByDebit(debit);
    }

    @Override
    public List<LigneTelephonique> ligneByDirection(Direction direction) {
        return ligneTelephoniqueRepository.findByDirection(direction);
    }

    @Override
    public List<LigneTelephonique> ligneByFonction(Fonction fonction) {
        return ligneTelephoniqueRepository.findByFonction(fonction);
    }

    @Override
    public List<LigneTelephonique> ligneByForfait(Forfait forfait) {
        return ligneTelephoniqueRepository.findByForfait(forfait);
    }

    @Override
    public List<LigneTelephonique> ligneByNature(NatureLigne natureLigne) {
        return ligneTelephoniqueRepository.findByNatureLigne(natureLigne);
    }

    @Override
    public List<LigneTelephonique> ligneByType(TypeLigne typeLigne) {
        return ligneTelephoniqueRepository.findByTypeLigne(typeLigne);
    }*/

    @Override
    public List<LigneTelephonique> ligneByDebit(Debit debit) {
        return ligneTelephoniqueRepository.findByAttributValue("debit", debit.toString());
    }

    @Override
    public List<LigneTelephonique> ligneByDirection(Direction direction) {
        return ligneTelephoniqueRepository.findByAttributValue("direction", direction.toString());
    }

    @Override
    public List<LigneTelephonique> ligneByFonction(Fonction fonction) {
        return ligneTelephoniqueRepository.findByAttributValue("fonction", fonction.toString());
    }

    @Override
    public List<LigneTelephonique> ligneByForfait(Forfait forfait) {
        return ligneTelephoniqueRepository.findByAttributValue("forfait", forfait.toString());
    }

    @Override
    public List<LigneTelephonique> ligneByNature(NatureLigne natureLigne) {
        return ligneTelephoniqueRepository.findByAttributValue("natureLigne", natureLigne.toString());
    }

    @Override
    public List<LigneTelephonique> ligneByType(TypeLigne typeLigne) {
        return ligneTelephoniqueRepository.findByAttributValue("typeLigne", typeLigne.toString());
    }



}
