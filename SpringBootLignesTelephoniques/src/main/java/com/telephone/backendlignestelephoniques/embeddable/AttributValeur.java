package com.telephone.backendlignestelephoniques.embeddable;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Embeddable
@Data
public class AttributValeur {
    private String nomAttribut;
    private String valeurAttribut;

    private String enumeration;
}