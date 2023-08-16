package com.telephone.backendlignestelephoniques.services.TypeLigne;

import com.telephone.backendlignestelephoniques.entities.TypeLigne;
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
public class TypeLigneServiceImpl implements TypeLigneService {
    @Override
    public TypeLigne saveTypeLigne(TypeLigne typeLigne) {
        return null;
    }

    @Override
    public TypeLigne getTypeLigne(Long typeLigneId) throws ElementNotFoundException {
        return null;
    }

    @Override
    public void deleteTypeLigne(Long id) {

    }

    @Override
    public TypeLigne updateTypeLigne(TypeLigne typeLigne) {
        return null;
    }

    @Override
    public List<TypeLigne> listTypeLigne() {
        return null;
    }
}
