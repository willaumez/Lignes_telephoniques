package com.telephone.backendlignestelephoniques.dtos;

import lombok.Data;

@Data
public class LigneAttributDto {
    private Long id;
    private AttributDto attribut;
    private String valeurAttribut;
}
