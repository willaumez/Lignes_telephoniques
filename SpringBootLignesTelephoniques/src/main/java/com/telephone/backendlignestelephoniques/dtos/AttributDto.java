package com.telephone.backendlignestelephoniques.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AttributDto {
    private Long idAttribut;
    private String nomAttribut;
    private String type;
    private String valeurAttribut;
    private List<String> enumeration;
}
