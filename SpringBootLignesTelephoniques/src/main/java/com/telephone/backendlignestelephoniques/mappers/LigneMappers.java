package com.telephone.backendlignestelephoniques.mappers;

import com.telephone.backendlignestelephoniques.dtos.AttributDto;
import com.telephone.backendlignestelephoniques.dtos.LigneAttributDto;
import com.telephone.backendlignestelephoniques.dtos.LigneTelephoniqueDto;
import com.telephone.backendlignestelephoniques.dtos.TypeLigneDto;
import com.telephone.backendlignestelephoniques.entities.Attribut;
import com.telephone.backendlignestelephoniques.entities.LigneAttribut;
import com.telephone.backendlignestelephoniques.entities.LigneTelephonique;
import com.telephone.backendlignestelephoniques.entities.TypeLigne;

import java.util.Set;

public interface LigneMappers {
    LigneTelephoniqueDto fromLigneTelephonique(LigneTelephonique ligneTelephonique);
    LigneTelephonique fromLigneTelephoniqueDto(LigneTelephoniqueDto ligneTelephoniqueDto);

////
    AttributDto fromAttribut(Attribut attribut);
    Attribut fromAttributDto(AttributDto attributDto);

////

    LigneAttributDto fromLigneAttribut(LigneAttribut ligneAttribut);
    LigneAttribut fromLigneAttributDto(LigneAttributDto ligneAttributDto);


////
    TypeLigneDto fromTypeLigne(TypeLigne typeLigne);
    TypeLigne fromTypeLigneDto(TypeLigneDto typeLigneDto);

////
    Set<String> fromAttributsToNames(Set<Attribut> attributs);



}
