package com.telephone.backendlignestelephoniques.entities;


import com.telephone.backendlignestelephoniques.enums.EtatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Corbeille {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRestoration;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date dateSuppression;

    //@Embedded
    //private Ligne ligneTelephonique;

/*    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Ligne {
        @Column(unique = true, nullable = false)
        private String numeroLigne;

        @ManyToOne
        private TypeLigne typeLigne;

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

        @ElementCollection
        @MapKeyColumn(name = "nomAttribut")
        @Column(name = "valeur")
        @CollectionTable(name = "ligne_attributs", joinColumns = @JoinColumn(name = "ligne_id"))
        private Map<String, String> attributs = new HashMap<>();
    }*/

}

