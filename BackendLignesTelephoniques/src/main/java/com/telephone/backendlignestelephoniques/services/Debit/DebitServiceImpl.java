package com.telephone.backendlignestelephoniques.services.Debit;

import com.telephone.backendlignestelephoniques.entities.Debit;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import com.telephone.backendlignestelephoniques.repositories.DebitRepository;
import com.telephone.backendlignestelephoniques.services.Historique.HistoriqueService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class DebitServiceImpl implements DebitService {

    private DebitRepository debitRepository;
    private HistoriqueService historiqueService;

    @Override
    public void saveDebit(Debit debit) {
        debitRepository.save(debit);
        historiqueService.saveHistoriques("Ajout [Debit]", debit.getNomDebit());
    }

    @Override
    public Debit getDebit(Long debitId) throws ElementNotFoundException {
        return debitRepository.findById(debitId)
                .orElseThrow(() -> new ElementNotFoundException("----- Debit non trouvé -----"));
    }

    @Override
    public void deleteDebit(Long id) throws ElementNotFoundException {
        Debit debit = debitRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Debit with id " + id + " not found"));

        debitRepository.deleteById(id);
        historiqueService.saveHistoriques("Suppression [Debit]", debit.getNomDebit());
    }

    @Override
    public Debit updateDebit(Debit debit) {
        Debit updatedDebit = debitRepository.save(debit);
        historiqueService.saveHistoriques("Mise à jour [Debit]", updatedDebit.getNomDebit());
        return updatedDebit;
    }

    @Override
    public List<Debit> listDebits() {
        return debitRepository.findAll();
    }


}
