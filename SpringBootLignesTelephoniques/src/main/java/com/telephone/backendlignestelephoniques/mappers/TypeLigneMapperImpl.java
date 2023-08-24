package com.telephone.backendlignestelephoniques.mappers;

import com.telephone.backendlignestelephoniques.dtos.TypeLigneDTO;
import com.telephone.backendlignestelephoniques.entities.TypeLigne;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class TypeLigneMapperImpl implements TypeLigneMapper {
    @Override
    public TypeLigne fromTypeLigneDTO(TypeLigneDTO typeLigneDTO) {
        TypeLigne typeLigne=new TypeLigne();
        BeanUtils.copyProperties(typeLigneDTO,typeLigne);
        return typeLigne;
    }
    @Override
    public TypeLigneDTO fromTypeLigne(TypeLigne typeLigne) {
        TypeLigneDTO typeLigneDTO=new TypeLigneDTO();
        BeanUtils.copyProperties(typeLigne,typeLigneDTO);
        return typeLigneDTO;
    }

}
