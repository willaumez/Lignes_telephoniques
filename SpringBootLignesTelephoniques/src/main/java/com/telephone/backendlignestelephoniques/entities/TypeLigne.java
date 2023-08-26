package com.telephone.backendlignestelephoniques.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeLigne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idType;

    @Column(unique = true, nullable = false)
    private String nomType;
    private String descriptionType;

    @CreatedDate
    private Date createdDate;

    //@ManyToMany(cascade = CascadeType.PERSIST)
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},fetch = FetchType.LAZY)
    private Set<Attribut> attributs = new HashSet<>();
}
