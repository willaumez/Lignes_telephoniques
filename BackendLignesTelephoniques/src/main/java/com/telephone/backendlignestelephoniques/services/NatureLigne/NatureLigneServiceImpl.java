package com.telephone.backendlignestelephoniques.services.NatureLigne;

import com.telephone.backendlignestelephoniques.entities.NatureLigne;
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
public class NatureLigneServiceImpl implements NatureLigneService {
    @Override
    public NatureLigne saveNatureLigne(NatureLigne natureLigne) {
        return null;
    }

    @Override
    public NatureLigne getNatureLigne(Long historiqueId) throws ElementNotFoundException {
        return null;
    }

    @Override
    public void deleteNatureLigne(Long id) {

    }

    @Override
    public NatureLigne updateNatureLigne(NatureLigne natureLigne) {
        return null;
    }

    @Override
    public List<NatureLigne> listNatureLigne() {
        return null;
    }
}
