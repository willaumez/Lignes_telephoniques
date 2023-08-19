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
public class LigneTelephonique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLigne;

    @Column(unique = true)
    private String numeroLigne;

    @ManyToOne
    private TypeLigne typeLigne;

    @ManyToOne
    private Direction direction;

    private String affectation;
    private String poste;
    @Enumerated(EnumType.STRING)
    private EtatType etat;
    private Date dateLivraison;
    @Column(unique = true)
    private String numeroSerie;
    private Double montant;
    @CreatedDate
    private Date createdDate;


    @OneToMany(mappedBy = "ligneTelephonique", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapKey(name = "nomAttribut")
    private Map<String, AttributValue> attributs = new HashMap<>();


    /*@ManyToOne
    private Forfait forfait;

    private String codePIN;
    private String codePUK;

    @ManyToOne
    private Fonction fonction;

    @ManyToOne
    private Debit debit;

    private String adresseIP;

    @ManyToOne
    private NatureLigne natureLigne;

    private String nomUtilisateur;*/





}