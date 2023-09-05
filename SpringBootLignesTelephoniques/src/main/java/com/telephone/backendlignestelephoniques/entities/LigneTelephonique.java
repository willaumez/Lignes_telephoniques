package com.telephone.backendlignestelephoniques.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.telephone.backendlignestelephoniques.enums.EtatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigneTelephonique {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idLigne;

    @Column(unique = true, nullable = false)
    private String numeroLigne;

    private String affectation;
    private String poste;

    @Enumerated(EnumType.STRING)
    private EtatType etat;

    private Date dateLivraison;

    //@Column(unique = true, nullable = false)
    private String numeroSerie;

    private Double montant;

    @CreatedDate
    private Date createdDate;

    @Column(name = "id_type_ligne", insertable = false, updatable = false)
    private Long typeId;

    @ManyToOne
    @JoinColumn(name = "id_type_ligne")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TypeLigne typeLigne;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ligne_id", referencedColumnName = "idLigne")
    private Set<LigneAttribut> ligneAttributs = new HashSet<>();

}