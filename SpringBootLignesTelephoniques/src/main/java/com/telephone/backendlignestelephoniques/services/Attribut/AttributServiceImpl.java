package com.telephone.backendlignestelephoniques.services.Attribut;

import com.telephone.backendlignestelephoniques.entities.Attribut;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.AttributRepository;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AttributServiceImpl implements AttributService {

    private AttributRepository attributRepository;
    private HistoriqueService historiqueService;

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

        attributRepository.deleteById(attributId);
        historiqueService.saveHistoriques("Suppression [Attribut]", attribut.getNomAttribut(), operateur);
    }

    @Override
    public Attribut updateAttribut(Attribut attribut, String operateur) {
        Attribut existingAttribut = attributRepository.findById(attribut.getIdAttribut())
                .orElseThrow(() -> new EntityNotFoundException("Attribut not found"));

        existingAttribut.setNomAttribut(attribut.getNomAttribut());
        existingAttribut.setType(attribut.getType());
        existingAttribut.setValeurDefaut(attribut.getValeurDefaut());
        existingAttribut.setEnumeration(attribut.getEnumeration());

        Attribut updatedAttribut = attributRepository.save(existingAttribut);
        historiqueService.saveHistoriques("Mise à jour [Attribut]", updatedAttribut.getNomAttribut(), operateur);
        return updatedAttribut;
    }

    @Override
    public List<Attribut> listAttribut() {
        return attributRepository.findAll();
    }
}
