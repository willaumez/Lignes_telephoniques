package com.telephone.backendlignestelephoniques.mappers;

import com.telephone.backendlignestelephoniques.dtos.AttributDto;
import com.telephone.backendlignestelephoniques.dtos.LigneAttributDto;
import com.telephone.backendlignestelephoniques.dtos.LigneTelephoniqueDto;
import com.telephone.backendlignestelephoniques.dtos.TypeLigneDto;
import com.telephone.backendlignestelephoniques.entities.Attribut;
import com.telephone.backendlignestelephoniques.entities.LigneAttribut;
import com.telephone.backendlignestelephoniques.entities.LigneTelephonique;
import com.telephone.backendlignestelephoniques.entities.TypeLigne;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LigneMappersImpl implements LigneMappers {

/*    @Override
    public LigneTelephoniqueDto fromLigneTelephonique(LigneTelephonique ligneTelephonique) {
        if (ligneTelephonique == null) return null;

        LigneTelephoniqueDto dto = new LigneTelephoniqueDto();
        dto.setIdLigne(ligneTelephonique.getIdLigne());
        dto.setNumeroLigne(ligneTelephonique.getNumeroLigne());
        dto.setAffectation(ligneTelephonique.getAffectation());
        dto.setPoste(ligneTelephonique.getPoste());
        dto.setEtat(ligneTelephonique.getEtat());
        dto.setDateLivraison(ligneTelephonique.getDateLivraison());
        dto.setNumeroSerie(ligneTelephonique.getNumeroSerie());
        dto.setMontant(ligneTelephonique.getMontant());
        dto.setCreatedDate(ligneTelephonique.getCreatedDate());
        dto.setTypeLigne(fromTypeLigne(ligneTelephonique.getTypeLigne()));

        Set<LigneAttributDto> ligneAttributDtos = new HashSet<>();
        for(LigneAttribut ligneAttribut : ligneTelephonique.getLigneAttributs()) {

            ligneAttributDtos.add(fromLigneAttribut(ligneAttribut));
        }
        dto.setLigneAttributs(ligneAttributDtos);
        return dto;
    }
    @Override
    public LigneTelephonique fromLigneTelephoniqueDto(LigneTelephoniqueDto ligneTelephoniqueDto) {
        if (ligneTelephoniqueDto == null) return null;

        LigneTelephonique entity = new LigneTelephonique();
        entity.setIdLigne(ligneTelephoniqueDto.getIdLigne());
        entity.setNumeroLigne(ligneTelephoniqueDto.getNumeroLigne());
        entity.setAffectation(ligneTelephoniqueDto.getAffectation());
        entity.setPoste(ligneTelephoniqueDto.getPoste());
        entity.setEtat(ligneTelephoniqueDto.getEtat());
        entity.setDateLivraison(ligneTelephoniqueDto.getDateLivraison());
        entity.setNumeroSerie(ligneTelephoniqueDto.getNumeroSerie());
        entity.setMontant(ligneTelephoniqueDto.getMontant());
        entity.setCreatedDate(ligneTelephoniqueDto.getCreatedDate());
        entity.setTypeLigne(fromTypeLigneDto(ligneTelephoniqueDto.getTypeLigne()));

        Set<LigneAttribut> ligneAttributs = new HashSet<>();
        for(LigneAttributDto ligneAttributDto : ligneTelephoniqueDto.getLigneAttributs()) {
            ligneAttributs.add(fromLigneAttributDto(ligneAttributDto));
        }
        entity.setLigneAttributs(ligneAttributs);
        return entity;
    }
    @Override
    public AttributDto fromAttribut(Attribut attribut) {
        if (attribut == null) return null;

        AttributDto dto = new AttributDto();
        dto.setIdAttribut(attribut.getIdAttribut());
        dto.setNomAttribut(attribut.getNomAttribut());
        dto.setType(attribut.getType());
        dto.setValeurAttribut(attribut.getValeurAttribut());
        dto.setEnumeration(attribut.getEnumeration());
        return dto;
    }
    @Override
    public Attribut fromAttributDto(AttributDto attributDto) {
        if (attributDto == null) return null;

        Attribut entity = new Attribut();
        entity.setIdAttribut(attributDto.getIdAttribut());
        entity.setNomAttribut(attributDto.getNomAttribut());
        entity.setType(attributDto.getType());
        entity.setValeurAttribut(attributDto.getValeurAttribut());
        entity.setEnumeration(attributDto.getEnumeration());
        return entity;
    }
    @Override
    public LigneAttributDto fromLigneAttribut(LigneAttribut ligneAttribut) {
        if (ligneAttribut == null) return null;

        LigneAttributDto dto = new LigneAttributDto();
        dto.setId(ligneAttribut.getId());
        dto.setAttribut(fromAttribut(ligneAttribut.getAttribut()));
        dto.setValeurAttribut(ligneAttribut.getValeurAttribut());
        return dto;
    }
    @Override
    public LigneAttribut fromLigneAttributDto(LigneAttributDto ligneAttributDto) {
        if (ligneAttributDto == null) return null;

        LigneAttribut entity = new LigneAttribut();
        entity.setId(ligneAttributDto.getId());
        entity.setAttribut(fromAttributDto(ligneAttributDto.getAttribut()));
        entity.setValeurAttribut(ligneAttributDto.getValeurAttribut());
        return entity;
    }
    @Override
    public TypeLigneDto fromTypeLigne(TypeLigne typeLigne) {
        if (typeLigne == null) return null;

        TypeLigneDto dto = new TypeLigneDto();
        dto.setIdType(typeLigne.getIdType());
        dto.setNomType(typeLigne.getNomType());
        dto.setDescriptionType(typeLigne.getDescriptionType());
        dto.setCreatedDate(typeLigne.getCreatedDate());

        Set<AttributDto> attributDtos = new HashSet<>();
        for (Attribut attribut : typeLigne.getAttributs()) {
            attributDtos.add(fromAttribut(attribut));
        }
        dto.setAttributs(attributDtos);

        return dto;
    }
    @Override
    public TypeLigne fromTypeLigneDto(TypeLigneDto typeLigneDto) {
        if (typeLigneDto == null) return null;

        TypeLigne entity = new TypeLigne();
        entity.setIdType(typeLigneDto.getIdType());
        entity.setNomType(typeLigneDto.getNomType());
        entity.setDescriptionType(typeLigneDto.getDescriptionType());
        entity.setCreatedDate(typeLigneDto.getCreatedDate());
        Set<Attribut> attributs = new HashSet<>();
        for (AttributDto attributDto : typeLigneDto.getAttributs()) {
            attributs.add(fromAttributDto(attributDto));
        }
        entity.setAttributs(attributs);
        return entity;
    }*/

    // Mapper pour LigneTelephonique
    @Override
    public LigneTelephoniqueDto fromLigneTelephonique(LigneTelephonique ligneTelephonique) {
        if (ligneTelephonique == null) return null;

        LigneTelephoniqueDto dto = new LigneTelephoniqueDto();
        dto.setIdLigne(ligneTelephonique.getIdLigne());
        dto.setNumeroLigne(ligneTelephonique.getNumeroLigne());
        dto.setAffectation(ligneTelephonique.getAffectation());
        dto.setPoste(ligneTelephonique.getPoste());
        dto.setEtat(ligneTelephonique.getEtat());
        dto.setDateLivraison(ligneTelephonique.getDateLivraison());
        dto.setNumeroSerie(ligneTelephonique.getNumeroSerie());
        dto.setMontant(ligneTelephonique.getMontant());
        dto.setCreatedDate(ligneTelephonique.getCreatedDate());
        dto.setTypeId(ligneTelephonique.getTypeId());

        if (ligneTelephonique.getLigneAttributs() != null) {
            dto.setLigneAttributs(
                    ligneTelephonique.getLigneAttributs().stream()
                            .filter(Objects::nonNull)
                            .map(this::fromLigneAttribut)
                            .collect(Collectors.toSet())
            );
        }

        return dto;
    }

    @Override
    public LigneTelephonique fromLigneTelephoniqueDto(LigneTelephoniqueDto dto) {
        if (dto == null) return null;

        LigneTelephonique entity = new LigneTelephonique();
        entity.setIdLigne(dto.getIdLigne());
        entity.setNumeroLigne(dto.getNumeroLigne());
        entity.setAffectation(dto.getAffectation());
        entity.setPoste(dto.getPoste());
        entity.setEtat(dto.getEtat());
        entity.setDateLivraison(dto.getDateLivraison());
        entity.setNumeroSerie(dto.getNumeroSerie());
        entity.setMontant(dto.getMontant());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setTypeId(dto.getTypeId());

        if (dto.getLigneAttributs() != null) {
            entity.setLigneAttributs(
                    dto.getLigneAttributs().stream()
                            .filter(Objects::nonNull)
                            .map(this::fromLigneAttributDto)
                            .collect(Collectors.toSet())
            );
        }

        return entity;
    }

    // Mapper pour LigneAttribut
    @Override
    public LigneAttribut fromLigneAttributDto(LigneAttributDto ligneAttributDto) {
        if (ligneAttributDto == null) return null;

        LigneAttribut entity = new LigneAttribut();
        entity.setId(ligneAttributDto.getId());
        entity.setLigneId(ligneAttributDto.getLigneId());
        entity.setAttribut(fromAttributDto(ligneAttributDto.getAttribut())); // Conversion d'AttributDto à Attribut
        entity.setValeurAttribut(ligneAttributDto.getValeurAttribut());

        return entity;
    }
    @Override
    public LigneAttributDto fromLigneAttribut(LigneAttribut ligneAttribut) {
        if (ligneAttribut == null) return null;

        LigneAttributDto dto = new LigneAttributDto();
        dto.setId(ligneAttribut.getId());
        dto.setLigneId(ligneAttribut.getLigneId());
        dto.setAttribut(toAttributDto(ligneAttribut.getAttribut())); // Conversion d'Attribut à AttributDto
        dto.setValeurAttribut(ligneAttribut.getValeurAttribut());

        return dto;
    }

    // Méthode pour convertir Attribut en AttributDto
    public AttributDto toAttributDto(Attribut attribut) {
        if (attribut == null) return null;

        AttributDto dto = new AttributDto();
        dto.setIdAttribut(attribut.getIdAttribut());
        dto.setNomAttribut(attribut.getNomAttribut());
        dto.setType(attribut.getType());
        dto.setValeurAttribut(attribut.getValeurAttribut());
        dto.setEnumeration(attribut.getEnumeration());

        return dto;
    }


    // Méthode pour convertir AttributDto en Attribut
    public Attribut fromAttributDto(Attribut attributDto) {
        if (attributDto == null) return null;

        Attribut entity = new Attribut();
        entity.setIdAttribut(attributDto.getIdAttribut());
        entity.setNomAttribut(attributDto.getNomAttribut());
        entity.setType(attributDto.getType());
        entity.setValeurAttribut(attributDto.getValeurAttribut());
        entity.setEnumeration(attributDto.getEnumeration());

        return entity;
    }

    // Mapper pour Attribut
    @Override
    public AttributDto fromAttribut(Attribut attribut) {
        if (attribut == null) return null;

        AttributDto dto = new AttributDto();
        dto.setIdAttribut(attribut.getIdAttribut());
        dto.setNomAttribut(attribut.getNomAttribut());
        dto.setType(attribut.getType());
        dto.setValeurAttribut(attribut.getValeurAttribut());
        dto.setEnumeration(attribut.getEnumeration());

        return dto;
    }

    @Override
    public Attribut fromAttributDto(AttributDto attributDto) {
        if (attributDto == null) return null;

        Attribut entity = new Attribut();
        entity.setIdAttribut(attributDto.getIdAttribut());
        entity.setNomAttribut(attributDto.getNomAttribut());
        entity.setType(attributDto.getType());
        entity.setValeurAttribut(attributDto.getValeurAttribut());
        entity.setEnumeration(attributDto.getEnumeration());

        return entity;
    }

    // Mapper pour TypeLigne
    @Override
    public TypeLigneDto fromTypeLigne(TypeLigne typeLigne) {
        if (typeLigne == null) return null;

        TypeLigneDto dto = new TypeLigneDto();
        dto.setIdType(typeLigne.getIdType());
        dto.setNomType(typeLigne.getNomType());
        dto.setDescriptionType(typeLigne.getDescriptionType());
        dto.setCreatedDate(typeLigne.getCreatedDate());

        if (typeLigne.getAttributs() != null) {
            dto.setAttributs(
                    typeLigne.getAttributs().stream()
                            .filter(Objects::nonNull)
                            .map(this::fromAttribut)
                            .collect(Collectors.toSet())
            );
        }

        return dto;
    }

    @Override
    public TypeLigne fromTypeLigneDto(TypeLigneDto typeLigneDto) {
        if (typeLigneDto == null) return null;

        TypeLigne entity = new TypeLigne();
        entity.setIdType(typeLigneDto.getIdType());
        entity.setNomType(typeLigneDto.getNomType());
        entity.setDescriptionType(typeLigneDto.getDescriptionType());
        entity.setCreatedDate(typeLigneDto.getCreatedDate());

        if (typeLigneDto.getAttributs() != null) {
            entity.setAttributs(
                    typeLigneDto.getAttributs().stream()
                            .filter(Objects::nonNull)
                            .map(this::fromAttributDto)
                            .collect(Collectors.toSet())
            );
        }

        return entity;
    }



    @Override
    public Set<String> fromAttributsToNames(Set<Attribut> attributs) {
        if (attributs == null) return null;

        Set<String> names = new HashSet<>();
        for (Attribut attribut : attributs) {
            names.add(attribut.getNomAttribut());
        }
        return names;
    }

}
