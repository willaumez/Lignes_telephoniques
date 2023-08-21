package com.telephone.backendlignestelephoniques.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributLigne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TypeLigne typeLigne;

    private String nomAttribut;
    private String type; // Le type de l'attribut (peut être "String", "Integer", etc.)
    private String referenceEntity; // Nom de l'entité référencée si l'attribut est une référence
}
