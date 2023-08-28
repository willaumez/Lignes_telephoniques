package com.telephone.backendlignestelephoniques.dtos;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributDto {
    private Long idAttribut;
    private String nomAttribut;
    private String type;
    private String valeurAttribut;
    private List<String> enumeration;
}