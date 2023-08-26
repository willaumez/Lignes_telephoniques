package com.telephone.backendlignestelephoniques.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigneAttribut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private LigneTelephonique ligneTelephonique;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Attribut attribut;

    private String valeurAttribut;
}
