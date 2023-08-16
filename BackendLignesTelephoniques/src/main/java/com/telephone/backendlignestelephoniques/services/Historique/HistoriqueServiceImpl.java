package com.telephone.backendlignestelephoniques.services.Historique;

import com.telephone.backendlignestelephoniques.entities.Historiques;
import com.telephone.backendlignestelephoniques.exceptions.ElementNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class HistoriqueServiceImpl implements HistoriqueService {
    @Override
    public Historiques saveHistoriques(Historiques historiques) {
        return null;
    }

    @Override
    public Historiques getHistoriques(Long historiqueId) throws ElementNotFoundException {
        return null;
    }

    @Override
    public void deleteHistoriques(Long id) {

    }

    @Override
    public Historiques updateHistoriques(Historiques historiques) {
        return null;
    }

    @Override
    public List<Historiques> listHistoriques() {
        return null;
    }
}
