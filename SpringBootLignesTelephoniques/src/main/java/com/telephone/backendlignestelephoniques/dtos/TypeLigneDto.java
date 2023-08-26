package com.telephone.backendlignestelephoniques.dtos;

import com.telephone.backendlignestelephoniques.entities.Attribut;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class TypeLigneDto {
    private Long idType;
    private String nomType;
    private String descriptionType;
    private Date createdDate;
    private Set<AttributDto> attributs;
}
