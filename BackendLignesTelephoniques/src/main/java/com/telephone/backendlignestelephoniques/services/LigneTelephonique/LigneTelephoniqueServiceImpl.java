package com.telephone.backendlignestelephoniques.services.LigneTelephonique;

import com.telephone.backendlignestelephoniques.entities.LigneTelephonique;
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
public class LigneTelephoniqueServiceImpl implements LigneTelephoniqueService {
    @Override
    public LigneTelephonique saveLigneTelephonique(LigneTelephonique ligneTelephonique) {
        return null;
    }

    @Override
    public LigneTelephonique getLigneTelephonique(Long ligneId) throws ElementNotFoundException {
        return null;
    }

    @Override
    public void deleteLigneTelephonique(Long id) {

    }

    @Override
    public LigneTelephonique updateLigneTelephonique(LigneTelephonique ligneTelephonique) {
        return null;
    }

    @Override
    public List<LigneTelephonique> listLigneTelephonique() {
        return null;
    }
}
