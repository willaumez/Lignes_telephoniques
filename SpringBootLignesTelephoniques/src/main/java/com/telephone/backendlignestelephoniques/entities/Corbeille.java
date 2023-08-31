package com.telephone.backendlignestelephoniques.entities;


import com.telephone.backendlignestelephoniques.dtos.LigneAttributDto;
import com.telephone.backendlignestelephoniques.embeddable.AttributValeur;
import com.telephone.backendlignestelephoniques.enums.EtatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import java.util.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Corbeille {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCorbeille;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date dateSuppression;

    //ligne téléphonique
    private String numeroLigne;
    private String affectation;
    private String poste;
    @Enumerated(EnumType.STRING)
    private EtatType etat;
    private Date dateLivraison;
    private String numeroSerie;
    private Double montant;
    private Date createdDate;
    //type
    private Long typeId;
    private String nomType;
    private String descriptionType;

    //attribut
    @ElementCollection
    private Set<AttributValeur> attributValeurs = new HashSet<>();

}

