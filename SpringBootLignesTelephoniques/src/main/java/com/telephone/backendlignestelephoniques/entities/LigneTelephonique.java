package com.telephone.backendlignestelephoniques.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.telephone.backendlignestelephoniques.enums.EtatType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigneTelephonique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLigne;

    @Column(unique = true, nullable = false)
    private String numeroLigne;

    private String affectation;
    private String poste;

    @Enumerated(EnumType.STRING)
    private EtatType etat;

    private Date dateLivraison;

    @Column(unique = true, nullable = false)
    private String numeroSerie;

    private Double montant;

    @CreatedDate
    private Date createdDate;

    @ManyToOne
    private TypeLigne typeLigne;

    @OneToMany(mappedBy = "ligneTelephonique", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<LigneAttribut> ligneAttributs = new HashSet<>();
}

