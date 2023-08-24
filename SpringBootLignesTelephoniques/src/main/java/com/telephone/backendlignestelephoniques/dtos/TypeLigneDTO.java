package com.telephone.backendlignestelephoniques.dtos;

import com.telephone.backendlignestelephoniques.entities.Attribut;
import com.telephone.backendlignestelephoniques.entities.LigneTelephonique;
import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@CrossOrigin("*")
public class TypeLigneDTO {
    private Long idType;
    private String nomType;
    private String descriptionType;
    private Date createdDate;
    private Set<LigneTelephonique> lignesTelephoniques = new HashSet<>();
    private Set<Attribut> attributs = new HashSet<>();
}
