package com.telephone.backendlignestelephoniques.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attribut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAttribut;

    @Column(unique = true, nullable = false)
    private String nomAttribut;

    private String type;
    private String valeurAttribut;

    @ElementCollection
    private List<String> enumeration = new ArrayList<>();

}
