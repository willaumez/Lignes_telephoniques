package com.telephone.backendlignestelephoniques.entities;

import com.telephone.backendlignestelephoniques.enums.EtatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

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

    @ManyToOne(cascade = CascadeType.ALL)
    private TypeLigne typeLigne;

}

