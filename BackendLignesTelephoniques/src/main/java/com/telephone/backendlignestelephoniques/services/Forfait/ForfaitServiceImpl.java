package com.telephone.backendlignestelephoniques.services.Forfait;

import com.telephone.backendlignestelephoniques.entities.Forfait;
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
public class ForfaitServiceImpl implements ForfaitService {
    @Override
    public Forfait saveForfait(Forfait forfait) {
        return null;
    }

    @Override
    public Forfait getForfait(Long forfaitId) throws ElementNotFoundException {
        return null;
    }

    @Override
    public void deleteForfait(Long id) {

    }

    @Override
    public Forfait updateForfait(Forfait forfait) {
        return null;
    }

    @Override
    public List<Forfait> listForfaits() {
        return null;
    }
}
