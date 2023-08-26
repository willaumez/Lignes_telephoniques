package com.telephone.backendlignestelephoniques.services.Attribut;

import com.telephone.backendlignestelephoniques.entities.Attribut;
import com.telephone.backendlignestelephoniques.entities.TypeLigne;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.mappers.LigneMappers;
import com.telephone.backendlignestelephoniques.repositories.AttributRepository;
import com.telephone.backendlignestelephoniques.repositories.TypeLigneRepository;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AttributServiceImpl implements AttributService {

    private AttributRepository attributRepository;
    private HistoriqueService historiqueService;
    private TypeLigneRepository typeLigneRepository;
    private LigneMappers ligneMappers;

    @Override
    public void saveAttribut(Attribut attribut, String operateur) {
        // Vérifiez si l'attribut existe déjà par son nom
        if (attributRepository.existsByNomAttribut(attribut.getNomAttribut())) {
            throw new IllegalArgumentException("Un attribut du même nom existe déjà.");
        }

        attributRepository.save(attribut);
        historiqueService.saveHistoriques("Ajout [Attribut]", attribut.getNomAttribut(), operateur);
    }

    @Override
    public Attribut getAttribut(Long attributId) throws ElementNotFoundException {
        return attributRepository.findById(attributId)
                .orElseThrow(() -> new ElementNotFoundException("Attribut not found"));
    }

    @Override
    public void deleteAttribut(Long attributId, String operateur) throws ElementNotFoundException {
        Attribut attribut = attributRepository.findById(attributId)
                .orElseThrow(() -> new ElementNotFoundException("Attribut not found"));

        // Supprimez les énumérations associées à l'attribut
        attribut.setEnumeration(null);

        List<TypeLigne> typeLignes = typeLigneRepository.findByAttributId(attributId);
        for (TypeLigne typeLigne : typeLignes) {
            typeLigne.getAttributs().remove(attribut);
            typeLigneRepository.save(typeLigne); // Enregistrez le type modifié
        }

        attributRepository.deleteById(attributId);
        historiqueService.saveHistoriques("Suppression [Attribut]", attribut.getNomAttribut(), operateur);
    }


    @Override
    public Attribut updateAttribut(Attribut attribut, String operateur) {
        Attribut existingAttribut = attributRepository.findById(attribut.getIdAttribut())
                .orElseThrow(() -> new EntityNotFoundException("Attribut not found"));

        existingAttribut.setNomAttribut(attribut.getNomAttribut());
        existingAttribut.setType(attribut.getType());
        existingAttribut.setValeurAttribut(attribut.getValeurAttribut());
        existingAttribut.setEnumeration(attribut.getEnumeration());

        List<TypeLigne> typeLignes = typeLigneRepository.findByAttributId(existingAttribut.getIdAttribut());
        for (TypeLigne typeLigne : typeLignes) {
            typeLigne.getAttributs().remove(existingAttribut); // Supprime l'attribut de la liste des attributs du type
            typeLigneRepository.save(typeLigne); // Enregistrez le type modifié
        }

        Attribut updatedAttribut = attributRepository.save(existingAttribut);
        historiqueService.saveHistoriques("Mise à jour [Attribut]", updatedAttribut.getNomAttribut(), operateur);
        return updatedAttribut;
    }
    @Override
    public List<Attribut> listAttribut() {
        return attributRepository.findAll();
    }

    @Override
    public Set<String> listAttributNames() {
        List<Attribut> attributs = attributRepository.findAll();  // Récupérer tous les attributs de la base de données
        return ligneMappers.fromAttributsToNames(new HashSet<>(attributs));  // Utiliser le mapper pour obtenir les noms
    }

}
