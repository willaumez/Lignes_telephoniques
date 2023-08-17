package com.telephone.backendlignestelephoniques.services.Fonction;

import com.telephone.backendlignestelephoniques.entities.Fonction;
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
public class FonctionServiceImpl implements FonctionService {
    @Override
    public void saveFonction(Fonction fonction) {
    }

    @Override
    public Fonction getFonction(Long fonctionId) throws ElementNotFoundException {
        return null;
    }

    @Override
    public void deleteFonction(Long id) {

    }

    @Override
    public Fonction updateFonction(Fonction fonction) {
        return null;
    }

    @Override
    public List<Fonction> listFonctions() {
        return null;
    }
}
