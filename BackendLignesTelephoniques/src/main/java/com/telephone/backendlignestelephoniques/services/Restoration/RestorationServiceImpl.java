package com.telephone.backendlignestelephoniques.services.Restoration;

import com.telephone.backendlignestelephoniques.entities.Restoration;
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
public class RestorationServiceImpl implements RestorationService {
    @Override
    public Restoration saveRestoration(Restoration restoration) {
        return null;
    }

    @Override
    public Restoration getRestoration(Long restorationId) throws ElementNotFoundException {
        return null;
    }

    @Override
    public void deleteRestoration(Long id) {

    }

    @Override
    public Restoration updateRestoration(Restoration restoration) {
        return null;
    }

    @Override
    public List<Restoration> listRestoration() {
        return null;
    }
}
