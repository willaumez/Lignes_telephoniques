package com.telephone.backendlignestelephoniques.entities;


import com.telephone.backendlignestelephoniques.enums.EtatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restoration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRestoration;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date dateSuppression;

    @Embedded
    private Ligne ligneTelephonique;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Ligne {
        @Column(unique = true)
        private String numeroLigne;

        @OneToOne
        private TypeLigne typeLigne;

        @OneToOne
        private Direction direction;

        private String affectation;
        private String poste;
        @Enumerated(EnumType.STRING)
        private EtatType etat;
        private Date dateLivraison;
        @Column(unique = true)
        private String numeroSerie;
        private Double montant;

        @OneToOne
        private Forfait forfait;

        private String codePIN;
        private String codePUK;

        @OneToOne
        private Fonction fonction;

        @OneToOne
        private Debit debit;

        private String adresseIP;

        @OneToOne
        private NatureLigne natureLigne;

        private String nomUtilisateur;
    }

}

