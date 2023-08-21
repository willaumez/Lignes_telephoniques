package com.telephone.backendlignestelephoniques.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private LigneTelephonique ligneTelephonique;

    private String nomAttribut;
    private String valeur;
    private Long referenceId; // ID de l'entité référencée
}
