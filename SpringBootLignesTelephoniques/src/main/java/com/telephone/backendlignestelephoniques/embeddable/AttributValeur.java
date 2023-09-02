package com.telephone.backendlignestelephoniques.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class AttributValeur {
    private String nomAttribut;
    private String valeurAttribut;

    private String enumeration;
}